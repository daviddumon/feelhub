package com.steambeat.web.resources.json;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.application.ReferenceService;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.opinion.Judgment;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.web.dto.OpinionData;
import com.steambeat.web.dto.ReferenceData;
import com.steambeat.web.dto.ReferenceDataFactory;
import com.steambeat.web.representation.ModelAndView;
import com.steambeat.web.search.OpinionSearch;
import org.json.JSONException;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.List;
import java.util.UUID;

public class JsonOpinionsResource extends ServerResource {

    @Inject
    public JsonOpinionsResource(final ReferenceService referenceService, final OpinionSearch opinionSearch, final ReferenceDataFactory referenceDataFactory, final KeywordService keywordService) {
        this.referenceService = referenceService;
        this.opinionSearch = opinionSearch;
        this.referenceDataFactory = referenceDataFactory;
        this.keywordService = keywordService;
    }

    @Get
    public ModelAndView represent() throws JSONException {
        final Form form = getQuery();
        final List<Opinion> opinions = doSearchWithQueryParameters(form);
        extractLanguageParameter(form);
        final List<OpinionData> opinionDatas = getOpinionDatas(opinions);
        return ModelAndView.createNew("json/opinions.json.ftl", MediaType.APPLICATION_JSON).with("opinionDatas", opinionDatas);
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
            final OpinionData opinionData = new OpinionData(opinion, referenceDatas);
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
