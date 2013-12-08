package com.feelhub.web.resources.api.topics;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.FeelhubApiException;
import com.feelhub.web.search.TopicSearch;
import com.feelhub.web.search.criteria.FromPeopleCriteria;
import com.feelhub.web.search.criteria.OrderSearchCriteria;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.List;

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
        String order = "order";
        String name = "from-people";
        return ModelAndView.createNew("api/topics.json.ftl").with("topicDatas", getTopicDatas(getQueryValue(query, order), getQueryValue(query, name), skip, limit));
    }

    private String getQueryValue(Form query, String name) {
        if (query != null) {
            return query.getFirstValue(name, "");
        }
        return "";
    }

    private List<TopicData> getTopicDatas(String order, String fromPeople, final int skip, final int limit) {
        final List<FeelhubLanguage> languages = Lists.newArrayList();
        languages.add(FeelhubLanguage.reference());
        languages.add(FeelhubLanguage.none());
        if (!CurrentUser.get().getLanguage().isReference() && !CurrentUser.get().getLanguage().isNone()) {
            languages.add(CurrentUser.get().getLanguage());
        }
        final List<Topic> topics = getTopicsSearch(order, fromPeople, skip, limit, languages).execute();
        final List<TopicData> topicDatas = Lists.newArrayList();
        for (final Topic topic : topics) {
            topicDatas.add(topicDataFactory.topicData(topic, CurrentUser.get().getLanguage()));
        }
        return topicDatas;
    }

    private TopicSearch getTopicsSearch(String order, String fromPeople, int skip, int limit, List<FeelhubLanguage> languages) {
        TopicSearch result = getDefaultSearch(skip, limit, languages);
        OrderSearchCriteria orderCriteria = OrderSearchCriteria.fromString(order);
        FromPeopleCriteria fromPeopleCriteria = FromPeopleCriteria.fromString(fromPeople);
        return fromPeopleCriteria.addCriteria(orderCriteria.addCriteria(result));
    }

    private TopicSearch getDefaultSearch(int skip, int limit, List<FeelhubLanguage> languages) {
        return topicSearch
                .withLimit(limit)
                .withSkip(skip)
                .withLanguages(languages);
    }

    private final TopicSearch topicSearch;
    private final TopicDataFactory topicDataFactory;
}
