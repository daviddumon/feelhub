package com.feelhub.web.resources;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.TopicSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.Order;
import org.restlet.data.Language;
import org.restlet.resource.*;

import java.util.List;

public class HomeResource extends ServerResource {

    @Inject
    public HomeResource(final TopicSearch topicSearch) {
        this.topicSearch = topicSearch;
    }

    @Get
    public ModelAndView represent() {
        final TopicDataFactory topicDataFactory = new TopicDataFactory();
        final List<Topic> topics = topicSearch.withSort("lastModificationDate", Order.DESCENDING).execute();
        List<TopicData> topicDatas = Lists.newArrayList();
        for (Topic topic : topics) {
            topicDatas.add(topicDataFactory.topicData(topic, CurrentUser.get().getLanguage()));
        }
        return ModelAndView.createNew("home.ftl")
                .with("topicDatas", topicDatas)
                .with("locales", FeelhubLanguage.availables())
                .with("preferedLanguage", getPreferedLanguage().getPrimaryTag());
    }

    private Language getPreferedLanguage() {
        if (getRequest().getClientInfo().getAcceptedLanguages().isEmpty()) {
            return Language.ENGLISH;
        }
        return getRequest().getClientInfo().getAcceptedLanguages().get(0).getMetadata();
    }

    private TopicSearch topicSearch;
}