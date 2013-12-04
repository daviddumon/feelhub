package com.feelhub.domain.alchemy;

import com.feelhub.application.search.TopicSearch;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.FirstFeelingCreatedEvent;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.*;
import com.google.common.eventbus.*;
import com.google.inject.Inject;
import org.slf4j.*;

import java.util.*;

public class AlchemyAnalyzer {

    @Inject
    public AlchemyAnalyzer(final NamedEntityProvider namedEntityProvider, final AlchemyRelationBinder alchemyRelationBinder, final TopicSearch topicSearch) {
        this.namedEntityProvider = namedEntityProvider;
        this.alchemyRelationBinder = alchemyRelationBinder;
        this.topicSearch = topicSearch;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onFirstFeelingCreated(final FirstFeelingCreatedEvent event) {
        analyze(Repositories.topics().getHttpTopic(event.getFeeling().getTopicId()));
    }

    void analyze(final HttpTopic httpTopic) {
        if (isAnalysable(httpTopic)) {
            LOGGER.debug("Running alchemy analysis for {}", httpTopic);
            final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().forTopicId(httpTopic.getId());
            if (alchemyAnalysisList.isEmpty()) {
                performAlchemyAnalysis(httpTopic);
            }
        }
    }

    private boolean isAnalysable(final HttpTopic httpTopic) {
        return httpTopic.getType() == HttpTopicType.Website || httpTopic.getType() == HttpTopicType.Article;
    }

    private void performAlchemyAnalysis(final HttpTopic httpTopic) {
        try {
            final List<NamedEntity> namedEntities = namedEntityProvider.entitiesFor(httpTopic);
            LOGGER.debug("{} entities found", namedEntities.size());
            final List<AlchemyEntity> entities = createTopicAndAlchemyEntities(namedEntities, httpTopic.getUserId());
            createRelations(httpTopic, entities);
        } catch (AlchemyException e) {

        }
    }

    private List<AlchemyEntity> createTopicAndAlchemyEntities(final List<NamedEntity> namedEntities, final UUID userId) {
        final List<AlchemyEntity> entities = Lists.newArrayList();
        for (final NamedEntity namedEntity : namedEntities) {
            if (!namedEntity.tags.isEmpty()) {
                entities.add(createTopicAndAlchemyEntity(namedEntity, userId));
            }
        }
        return entities;
    }

    private AlchemyEntity createTopicAndAlchemyEntity(final NamedEntity namedEntity, final UUID userId) {
        final RealTopic realTopic = lookUpOrCreateTopic(namedEntity, userId);
        return findOrCreateAlchemyEntity(namedEntity, realTopic);
    }

    private AlchemyEntity findOrCreateAlchemyEntity(final NamedEntity namedEntity, final RealTopic realTopic) {
        final Optional<AlchemyEntity> alchemyEntityOptional = existingAlchemyEntity(realTopic.getId());
        if (alchemyEntityOptional.isPresent()) {
            return alchemyEntityOptional.get();
        }
        return createAlchemyEntity(namedEntity, realTopic);
    }

    private Optional<AlchemyEntity> existingAlchemyEntity(final UUID topicId) {
        final List<AlchemyEntity> alchemyEntities = Repositories.alchemyEntities().forTopicId(topicId);
        if (alchemyEntities.isEmpty()) {
            return Optional.absent();
        }
        return Optional.of(alchemyEntities.get(0));
    }

    private AlchemyEntity createAlchemyEntity(final NamedEntity namedEntity, final RealTopic realTopic) {
        final AlchemyEntity alchemyEntity = new AlchemyEntityFactory().create(namedEntity, realTopic);
        Repositories.alchemyEntities().add(alchemyEntity);
        return alchemyEntity;
    }

    private RealTopic lookUpOrCreateTopic(final NamedEntity namedEntity, final UUID userId) {
        final Optional<Topic> realTopic = topicSearch.lookUpTopic(namedEntity.tags.get(0), namedEntity.type, namedEntity.feelhubLanguage);
        return (RealTopic) realTopic.or(newTopic(namedEntity, userId));
    }

    private Supplier<Topic> newTopic(final NamedEntity namedEntity, final UUID userId) {
        return new Supplier<Topic>() {
            @Override
            public Topic get() {
                return createTopic(namedEntity, userId);
            }
        };
    }

    private RealTopic createTopic(final NamedEntity namedEntity, final UUID userId) {
        final RealTopic realTopic = new TopicFactory().createRealTopic(namedEntity.feelhubLanguage, namedEntity.tags.get(0), namedEntity.type, userId);
        Repositories.topics().add(realTopic);
        final TopicIndexer indexer = new TopicIndexer(realTopic);
        for (final String tag : namedEntity.tags) {
            indexer.index(tag, namedEntity.feelhubLanguage);
        }
        return realTopic;
    }

    private void createRelations(final HttpTopic topic, final List<AlchemyEntity> entities) {
        final HashMap<UUID, Double> topicsAndScores = Maps.newHashMap();
        for (final AlchemyEntity entity : entities) {
            topicsAndScores.put(entity.getTopicId(), entity.getRelevance());
        }
        alchemyRelationBinder.bind(topic.getId(), topicsAndScores);
    }

    private final AlchemyRelationBinder alchemyRelationBinder;
    private final NamedEntityProvider namedEntityProvider;
    private final TopicSearch topicSearch;
    private static final Logger LOGGER = LoggerFactory.getLogger(AlchemyAnalyzer.class);
}
