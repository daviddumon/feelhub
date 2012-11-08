package com.feelhub.web.resources.json;

import com.feelhub.application.*;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.FeelingSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class JsonNewFeelingsResource extends ServerResource {

    @Inject
    public JsonNewFeelingsResource(final KeywordService keywordService, final ReferenceDataFactory referenceDataFactory, final FeelingSearch feelingSearch, final ReferenceService referenceService) {
        this.keywordService = keywordService;
        this.referenceDataFactory = referenceDataFactory;
        this.feelingSearch = feelingSearch;
        this.referenceService = referenceService;
    }

    @Get
    public ModelAndView represent() {
        final List<Feeling> feelings = doSearchWithQueryParameters(getQuery());
        final List<FeelingData> feelingDatas = getFeelingDatas(feelings);
        setStatus(Status.SUCCESS_OK);
        return ModelAndView.createNew("json/newfeelings.json.ftl", MediaType.APPLICATION_JSON).with("feelingDatas", feelingDatas);
    }

    private List<Feeling> doSearchWithQueryParameters(final Form form) {
        final List<Feeling> feelings = Lists.newArrayList();
        setUpSearchForReferenceIdParameter(form);
        getLastParameter(form);
        extractLanguageParameter(form);
        feelingSearch.withSkip(0);
        feelingSearch.withLimit(30);
        final List<Feeling> searchResult = feelingSearch.withSort("creationDate", FeelingSearch.REVERSE_ORDER).execute();
        int i = 0;
        boolean found = false;
        while (!found & i < searchResult.size()) {
            if (searchResult.get(i).getId().equals(lastFeelingId)) {
                found = true;
            } else {
                feelings.add(searchResult.get(i++));
                if (i % 30 == 0) {
                    feelingSearch.reset();
                    feelingSearch.withSkip(i).withLimit(30).withSort("creationDate", FeelingSearch.REVERSE_ORDER).withReference(reference);
                    searchResult.addAll(feelingSearch.execute());
                }
            }
        }
        return feelings;
    }

    private void setUpSearchForReferenceIdParameter(final Form form) {
        if (form.getQueryString().contains("referenceId")) {
            reference = referenceService.lookUp(UUID.fromString(form.getFirstValue("referenceId").trim()));
            feelingSearch.withReference(reference);
        }
    }

    private void getLastParameter(final Form form) {
        if (form.getQueryString().contains("lastFeelingId")) {
            lastFeelingId = UUID.fromString(form.getFirstValue("lastFeelingId").trim());
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

    private final KeywordService keywordService;
    private final ReferenceDataFactory referenceDataFactory;
    private final FeelingSearch feelingSearch;
    private final ReferenceService referenceService;
    private FeelhubLanguage feelhubLanguage;
    private UUID lastFeelingId;
    private Reference reference;
}
