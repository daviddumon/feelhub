package com.feelhub.web.resources.json;

import com.feelhub.application.*;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.opinion.*;
import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.OpinionSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class JsonNewOpinionsResource extends ServerResource {

    @Inject
    public JsonNewOpinionsResource(final KeywordService keywordService, final ReferenceDataFactory referenceDataFactory, final OpinionSearch opinionSearch, final ReferenceService referenceService) {
        this.keywordService = keywordService;
        this.referenceDataFactory = referenceDataFactory;
        this.opinionSearch = opinionSearch;
        this.referenceService = referenceService;
    }

    @Get
    public ModelAndView represent() {
        final List<Opinion> opinions = doSearchWithQueryParameters(getQuery());
        final List<OpinionData> opinionDatas = getOpinionDatas(opinions);
        setStatus(Status.SUCCESS_OK);
        return ModelAndView.createNew("json/newopinions.json.ftl", MediaType.APPLICATION_JSON).with("opinionDatas", opinionDatas);
    }

    private List<Opinion> doSearchWithQueryParameters(final Form form) {
        final List<Opinion> opinions = Lists.newArrayList();
        setUpSearchForReferenceIdParameter(form);
        getLastParameter(form);
        extractLanguageParameter(form);
        opinionSearch.withSkip(0);
        opinionSearch.withLimit(30);
        final List<Opinion> searchResult = opinionSearch.withSort("creationDate", OpinionSearch.REVERSE_ORDER).execute();
        int i = 0;
        boolean found = false;
        while (!found & i < searchResult.size()) {
            if (searchResult.get(i).getId().equals(lastOpinionId)) {
                found = true;
            } else {
                opinions.add(searchResult.get(i++));
                if (i % 30 == 0) {
                    opinionSearch.reset();
                    opinionSearch.withSkip(i).withLimit(30).withSort("creationDate", OpinionSearch.REVERSE_ORDER).withReference(reference);
                    searchResult.addAll(opinionSearch.execute());
                }
            }
        }
        return opinions;
    }

    private void setUpSearchForReferenceIdParameter(final Form form) {
        if (form.getQueryString().contains("referenceId")) {
            reference = referenceService.lookUp(UUID.fromString(form.getFirstValue("referenceId").trim()));
            opinionSearch.withReference(reference);
        }
    }

    private void getLastParameter(final Form form) {
        if (form.getQueryString().contains("lastOpinionId")) {
            lastOpinionId = UUID.fromString(form.getFirstValue("lastOpinionId").trim());
        }
    }

    private void extractLanguageParameter(final Form form) {
        if (form.getQueryString().contains("languageCode")) {
            feelhubLanguage = FeelhubLanguage.forString(form.getFirstValue("languageCode").trim());
        } else {
            feelhubLanguage = FeelhubLanguage.reference();
        }
    }

    private List<OpinionData> getOpinionDatas(final List<Opinion> opinions) {
        final List<OpinionData> opinionDatas = Lists.newArrayList();
        for (final Opinion opinion : opinions) {
            final List<ReferenceData> referenceDatas = getReferenceDatas(opinion);
            final OpinionData opinionData = new OpinionData(opinion, referenceDatas);
            opinionDatas.add(opinionData);
        }
        return opinionDatas;
    }

    private List<ReferenceData> getReferenceDatas(final Opinion opinion) {
        final List<ReferenceData> referenceDatas = Lists.newArrayList();
        for (final Judgment judgment : opinion.getJudgments()) {
            final Keyword keyword = keywordService.lookUp(judgment.getReferenceId(), feelhubLanguage);
            final ReferenceData referenceData = referenceDataFactory.getReferenceDatas(keyword, judgment);
            referenceDatas.add(referenceData);
        }
        return referenceDatas;
    }

    private final KeywordService keywordService;
    private final ReferenceDataFactory referenceDataFactory;
    private final OpinionSearch opinionSearch;
    private final ReferenceService referenceService;
    private FeelhubLanguage feelhubLanguage;
    private UUID lastOpinionId;
    private Reference reference;
}
