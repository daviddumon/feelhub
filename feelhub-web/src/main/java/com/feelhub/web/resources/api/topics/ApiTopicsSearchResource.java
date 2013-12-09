package com.feelhub.web.resources.api.topics;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.authentification.WebUser;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.FeelhubApiException;
import com.feelhub.web.search.TopicSearch;
import com.feelhub.web.search.criteria.FromPeopleSearchCriteria;
import com.feelhub.web.search.criteria.OrderSearchCriteria;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.List;
import java.util.UUID;

public class ApiTopicsSearchResource extends ServerResource {

    @Inject
    public ApiTopicsSearchResource(final TopicSearch topicSearch, final TopicDataFactory topicDataFactory) {
        this.topicSearch = topicSearch;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView represent() {
        int limit = 100;
        int skip = 0;
        final Form query = getQuery();
        if (query != null) {
            if (query.getQueryString().contains("limit")) {
                limit = Integer.parseInt(query.getFirstValue("limit").trim());
                if (limit > 100) {
                    throw new FeelhubApiException();
                }
            }
            if (query.getQueryString().contains("skip")) {
                skip = Integer.parseInt(query.getFirstValue("skip").trim());
            }
        }
        List<TopicData> datas = getTopicDatas(getUserId(), getQueryValue(query, "order"), getQueryValue(query, "from-people"), skip, limit);
        return ModelAndView.createNew("api/topics.json.ftl").with("topicDatas", datas);
    }

    private List<TopicData> getTopicDatas(UUID userId, String order, String fromPeople, final int skip, final int limit) {
        final List<Topic> topics = getTopicsSearch(userId, order, fromPeople, skip, limit, getLanguages()).execute();
        return getTopicDatas(topics);
    }

    private TopicSearch getTopicsSearch(UUID userId, String order, String fromPeople, int skip, int limit, List<FeelhubLanguage> languages) {
        OrderSearchCriteria orderCriteria = OrderSearchCriteria.fromString(order);
        FromPeopleSearchCriteria fromPeopleSearchCriteria = FromPeopleSearchCriteria.fromString(fromPeople);
        return fromPeopleSearchCriteria.addCriteria(userId, orderCriteria.addCriteria(getDefaultSearch(skip, limit, languages)));
    }

    private TopicSearch getDefaultSearch(int skip, int limit, List<FeelhubLanguage> languages) {
        return topicSearch
                .withLimit(limit)
                .withSkip(skip)
                .withLanguages(languages);
    }

    private List<TopicData> getTopicDatas(List<Topic> topics) {
        final List<TopicData> topicDatas = Lists.newArrayList();
        for (final Topic topic : topics) {
            topicDatas.add(topicDataFactory.topicData(topic, CurrentUser.get().getLanguage()));
        }
        return topicDatas;
    }

    private List<FeelhubLanguage> getLanguages() {
        final List<FeelhubLanguage> languages = Lists.newArrayList();
        languages.add(FeelhubLanguage.reference());
        languages.add(FeelhubLanguage.none());
        if (!CurrentUser.get().getLanguage().isReference() && !CurrentUser.get().getLanguage().isNone()) {
            languages.add(CurrentUser.get().getLanguage());
        }
        return languages;
    }

    private String getQueryValue(Form query, String name) {
        if (query != null) {
            return query.getFirstValue(name, "");
        }
        return "";
    }

    private UUID getUserId() {
        WebUser webUser = CurrentUser.get();
        if (!webUser.isAuthenticated()) {
            return null;
        }
        return webUser.getUser().getId();
    }

    private final TopicSearch topicSearch;
    private final TopicDataFactory topicDataFactory;
}
