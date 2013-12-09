package com.feelhub.web.resources.api.topics;

import com.feelhub.domain.topic.Topic;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.TopicFullTextSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.List;

public class ApiTopicsFullTextSearchResource extends ServerResource {

    @Inject
    public ApiTopicsFullTextSearchResource(TopicFullTextSearch search, TopicDataFactory topicDataFactory) {
        this.search = search;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView search() {
        Object datas = doSearch();
        return ModelAndView.createNew("api/topics.json.ftl").with("topicDatas", datas);
    }

    private List<TopicData> doSearch() {
        List<TopicData> result = Lists.newArrayList();
        List<Topic> topics = search.execute(getQuery().getFirstValue("q"));
        for (Topic topic : topics) {
            result.add(topicDataFactory.topicData(topic, CurrentUser.get().getLanguage()));
        }
        return result;
    }

    private final TopicFullTextSearch search;
    private final TopicDataFactory topicDataFactory;
}
