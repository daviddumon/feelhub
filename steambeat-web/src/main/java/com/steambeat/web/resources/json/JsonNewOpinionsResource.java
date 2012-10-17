package com.steambeat.web.resources.json;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.web.dto.*;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import com.steambeat.web.search.OpinionSearch;
import org.restlet.data.*;
import org.restlet.representation.Representation;
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
    public Representation getNewOpinions() {
        List<Opinion> opinions = doSearchWithQueryParameters(getQuery());
        final List<OpinionData> opinionDatas = getOpinionDatas(opinions);
        setStatus(Status.SUCCESS_OK);
        return SteambeatTemplateRepresentation.createNew("json/newopinions.json.ftl", getContext(), MediaType.APPLICATION_JSON, getRequest()).with("opinionDatas", opinionDatas);
    }

    private List<Opinion> doSearchWithQueryParameters(final Form form) {
        List<Opinion> opinions = Lists.newArrayList();
        setUpSearchForReferenceIdParameter(form);
        getLastParameter(form);
        extractLanguageParameter(form);
        opinionSearch.withSkip(0);
        opinionSearch.withLimit(30);
        List<Opinion> searchResult = opinionSearch.withSort("creationDate", OpinionSearch.REVERSE_ORDER).execute();
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
        } else {
            throw new SteambeatJsonException();
        }
    }

    private void getLastParameter(final Form form) {
        if (form.getQueryString().contains("lastOpinionId")) {
            lastOpinionId = UUID.fromString(form.getFirstValue("lastOpinionId").trim());
        } else {
            throw new SteambeatJsonException();
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

    private KeywordService keywordService;
    private ReferenceDataFactory referenceDataFactory;
    private OpinionSearch opinionSearch;
    private ReferenceService referenceService;
    private SteambeatLanguage steambeatLanguage;
    private UUID lastOpinionId;
    private Reference reference;
}
