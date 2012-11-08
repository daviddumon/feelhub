package com.feelhub.web.resources.json;

import com.feelhub.application.*;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.keyword.Keyword;
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

public class JsonFeelingsResource extends ServerResource {

    @Inject
    public JsonFeelingsResource(final ReferenceService referenceService, final FeelingSearch feelingSearch, final ReferenceDataFactory referenceDataFactory, final KeywordService keywordService) {
        this.referenceService = referenceService;
        this.feelingSearch = feelingSearch;
        this.referenceDataFactory = referenceDataFactory;
        this.keywordService = keywordService;
    }

    @Get
    public ModelAndView represent() throws JSONException {
        final Form form = getQuery();
        final List<Feeling> feelings = doSearchWithQueryParameters(form);
        extractLanguageParameter(form);
        final List<FeelingData> feelingDatas = getFeelingDatas(feelings);
        return ModelAndView.createNew("json/feelings.json.ftl", MediaType.APPLICATION_JSON).with("feelingDatas", feelingDatas);
    }

    private List<Feeling> doSearchWithQueryParameters(final Form form) {
        setUpSearchForLimitParameter(form);
        setUpSearchForSkipParameter(form);
        setUpSearchForReferenceIdParameter(form);
        return feelingSearch.withSort("creationDate", FeelingSearch.REVERSE_ORDER).execute();
    }

    private void setUpSearchForLimitParameter(final Form form) {
        if (form.getQueryString().contains("limit")) {
            final int limit = Integer.parseInt(form.getFirstValue("limit").trim());
            if (limit > 100) {
                throw new FeelhubJsonException();
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

    private void setUpSearchForReferenceIdParameter(final Form form) {
        if (form.getQueryString().contains("referenceId")) {
            feelingSearch.withReference(referenceService.lookUp(UUID.fromString(form.getFirstValue("referenceId").trim())));
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
            final List<ReferenceData> referenceDatas = getReferenceDatas(feeling);
            final FeelingData feelingData = new FeelingData(feeling, referenceDatas);
            feelingDatas.add(feelingData);
        }
        return feelingDatas;
    }

    private List<ReferenceData> getReferenceDatas(final Feeling feeling) {
        final List<ReferenceData> referenceDatas = Lists.newArrayList();
        for (final Sentiment sentiment : feeling.getSentiments()) {
            final Keyword keyword = keywordService.lookUp(sentiment.getReferenceId(), feelhubLanguage);
            final ReferenceData referenceData = referenceDataFactory.getReferenceDatas(keyword, sentiment);
            referenceDatas.add(referenceData);
        }
        return referenceDatas;
    }

    private final FeelingSearch feelingSearch;
    private final ReferenceDataFactory referenceDataFactory;
    private final KeywordService keywordService;
    private final ReferenceService referenceService;
    private FeelhubLanguage feelhubLanguage;
}
