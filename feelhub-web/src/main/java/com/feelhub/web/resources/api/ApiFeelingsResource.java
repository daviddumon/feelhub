package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.FeelingSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.json.JSONException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class ApiFeelingsResource extends ServerResource {

    @Inject
    public ApiFeelingsResource(final TopicService topicService, final FeelingSearch feelingSearch, final KeywordDataFactory keywordDataFactory) {
        this.topicService = topicService;
        this.feelingSearch = feelingSearch;
        this.keywordDataFactory = keywordDataFactory;
    }

    @Get
    public ModelAndView represent() throws JSONException {
        final Form form = getQuery();
        final List<Feeling> feelings = doSearchWithQueryParameters(form);
        extractLanguageParameter(form);
        final List<FeelingData> feelingDatas = getFeelingDatas(feelings);
        return ModelAndView.createNew("api/feelings.json.ftl", MediaType.APPLICATION_JSON).with("feelingDatas", feelingDatas);
    }

    private List<Feeling> doSearchWithQueryParameters(final Form form) {
        setUpSearchForLimitParameter(form);
        setUpSearchForSkipParameter(form);
        setUpSearchForTopicIdParameter(form);
        return feelingSearch.withSort("creationDate", FeelingSearch.REVERSE_ORDER).execute();
    }

    private void setUpSearchForLimitParameter(final Form form) {
        if (form.getQueryString().contains("limit")) {
            final int limit = Integer.parseInt(form.getFirstValue("limit").trim());
            if (limit > 100) {
                throw new FeelhubApiException();
            }
            feelingSearch.withLimit(limit);
        } else {
            feelingSearch.withLimit(100);
        }
    }

    private void setUpSearchForSkipParameter(final Form form) {
        if (form.getQueryString().contains("skip")) {
            feelingSearch.withSkip(Integer.parseInt(form.getFirstValue("skip").trim()));
        } else {
            feelingSearch.withSkip(0);
        }
    }

    private void setUpSearchForTopicIdParameter(final Form form) {
        if (form.getQueryString().contains("topicId")) {
            feelingSearch.withTopicId(UUID.fromString(form.getFirstValue("topicId").trim()));
        }
    }

    private void extractLanguageParameter(final Form form) {
        if (form.getQueryString().contains("languageCode")) {
            feelhubLanguage = FeelhubLanguage.forString(form.getFirstValue("languageCode").trim());
        } else {
            feelhubLanguage = FeelhubLanguage.reference();
        }
    }

    private List<FeelingData> getFeelingDatas(final List<Feeling> feelings) {
        final List<FeelingData> feelingDatas = Lists.newArrayList();
        for (final Feeling feeling : feelings) {
            final List<KeywordData> keywordDatas = keywordDataFactory.getKeywordDatas(feeling, feelhubLanguage);
            final FeelingData feelingData = new FeelingData(feeling, keywordDatas);
            feelingDatas.add(feelingData);
        }
        return feelingDatas;
    }

    private final FeelingSearch feelingSearch;
    private final KeywordDataFactory keywordDataFactory;
    private final TopicService topicService;
    private FeelhubLanguage feelhubLanguage;
}
