package com.steambeat.web.resources.json;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.web.dto.*;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import com.steambeat.web.search.OpinionSearch;
import org.json.JSONException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class JsonOpinionsResource extends ServerResource {

    @Inject
    public JsonOpinionsResource(final ReferenceService referenceService, final OpinionSearch opinionSearch, final ReferenceDataFactory referenceDataFactory, final KeywordService keywordService) {
        this.referenceService = referenceService;
        this.opinionSearch = opinionSearch;
        this.referenceDataFactory = referenceDataFactory;
        this.keywordService = keywordService;
    }

    @Get
    public SteambeatTemplateRepresentation represent() throws JSONException {
        final Form form = getQuery();
        final List<Opinion> opinions = doSearchWithQueryParameters(form);
        extractLanguageParameter(form);
        final List<OpinionData> opinionDatas = getOpinionDatas(opinions);
        return SteambeatTemplateRepresentation.createNew("json/opinions.json.ftl", getContext(), MediaType.APPLICATION_JSON, getRequest()).with("opinionDatas", opinionDatas);
    }

    private List<Opinion> doSearchWithQueryParameters(final Form form) {
        setUpSearchForLimitParameter(form);
        setUpSearchForSkipParameter(form);
        setUpSearchForReferenceIdParameter(form);
        return opinionSearch.withSort("creationDate", OpinionSearch.REVERSE_ORDER).execute();
    }

    private void setUpSearchForLimitParameter(final Form form) {
        if (form.getQueryString().contains("limit")) {
            final int limit = Integer.parseInt(form.getFirstValue("limit").trim());
            if (limit > 100) {
                throw new SteambeatJsonException();
            }
            opinionSearch.withLimit(limit);
        } else {
            opinionSearch.withLimit(100);
        }
    }

    private void setUpSearchForSkipParameter(final Form form) {
        if (form.getQueryString().contains("skip")) {
            opinionSearch.withSkip(Integer.parseInt(form.getFirstValue("skip").trim()));
        } else {
            opinionSearch.withSkip(0);
        }
    }

    private void setUpSearchForReferenceIdParameter(final Form form) {
        if (form.getQueryString().contains("referenceId")) {
            opinionSearch.withReference(referenceService.lookUp(UUID.fromString(form.getFirstValue("referenceId").trim())));
        }
    }

    private void extractLanguageParameter(final Form form) {
        if (form.getQueryString().contains("languageCode")) {
            steambeatLanguage = SteambeatLanguage.forString(form.getFirstValue("languageCode").trim());
        } else {
            steambeatLanguage = SteambeatLanguage.reference();
        }
    }

    private List<OpinionData> getOpinionDatas(final List<Opinion> opinions) {
        final List<OpinionData> opinionDatas = Lists.newArrayList();
        for (final Opinion opinion : opinions) {
            final List<ReferenceData> referenceDatas = getReferenceDatas(opinion);
            final OpinionData opinionData = new OpinionData(opinion.getText(), referenceDatas);
            opinionDatas.add(opinionData);
        }
        return opinionDatas;
    }

    private List<ReferenceData> getReferenceDatas(final Opinion opinion) {
        final List<ReferenceData> referenceDatas = Lists.newArrayList();
        for (final Judgment judgment : opinion.getJudgments()) {
            final Keyword keyword = keywordService.lookUp(judgment.getReferenceId(), steambeatLanguage);
            final ReferenceData referenceData = referenceDataFactory.getReferenceDatas(keyword, judgment);
            referenceDatas.add(referenceData);
        }
        return referenceDatas;
    }

    private final OpinionSearch opinionSearch;
    private final ReferenceDataFactory referenceDataFactory;
    private final KeywordService keywordService;
    private final ReferenceService referenceService;
    private SteambeatLanguage steambeatLanguage;
}
