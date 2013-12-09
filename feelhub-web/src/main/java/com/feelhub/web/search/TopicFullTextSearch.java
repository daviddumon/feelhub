package com.feelhub.web.search;

import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.web.authentification.CurrentUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jongo.Jongo;
import org.mongolink.domain.criteria.Criteria;
import org.mongolink.domain.criteria.Restrictions;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Singleton
public class TopicFullTextSearch {

    @Inject
    public TopicFullTextSearch(Jongo jongo, SessionProvider sessionProvider) {
        this.jongo = jongo;
        this.sessionProvider = sessionProvider;
    }

    public List<Topic> execute(String searchString) {
        TagSearchResults tags = jongo.runCommand("{text:#, search:#}", "tag", searchString).as(TagSearchResults.class);
        Criteria<Topic> criteria = sessionProvider.get().createCriteria(Topic.class);
        criteria.add(Restrictions.in("_id", extractTopics(tags)));
        return criteria.list();
    }

    private List<UUID> extractTopics(TagSearchResults tags) {
        List<UUID> topicIds = Lists.newArrayList();
        for (TagSearchResult result : tags.results) {
            topicIds.addAll(extactTopicsIdFromTag(result.obj));
        }
        return topicIds;
    }

    private Set<UUID> extactTopicsIdFromTag(Tag tag) {
        Set<UUID> topics = Sets.newHashSet();
        List<FeelhubLanguage> languages = Lists.newArrayList(FeelhubLanguage.none(), FeelhubLanguage.REFERENCE, CurrentUser.get().getLanguage());
        for (FeelhubLanguage language : languages) {
            topics.addAll(tag.getTopicsIdFor(language));
        }
        return topics;
    }

    private final Jongo jongo;
    private final SessionProvider sessionProvider;
}
