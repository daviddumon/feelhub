package com.steambeat.web.resources.json;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.relation.Relation;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.web.dto.ReferenceData;
import com.steambeat.web.dto.ReferenceDataFactory;
import com.steambeat.web.representation.ModelAndView;
import com.steambeat.web.search.RelationSearch;
import com.steambeat.web.search.Search;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.List;
import java.util.UUID;

public class JsonRelatedResource extends ServerResource {

    @Inject
    public JsonRelatedResource(final RelationSearch relationSearch, final KeywordService keywordService, final ReferenceDataFactory referenceDataFactory) {
        this.relationSearch = relationSearch;
        this.keywordService = keywordService;
        this.referenceDataFactory = referenceDataFactory;
    }

    @Get
    public ModelAndView represent() {
        doSearchWithQueryParameters();
        getReferenceDataForEachRelation();
        return ModelAndView.createNew("json/related.json.ftl", MediaType.APPLICATION_JSON).with("referenceDataList", referenceDataList);
    }

    private void doSearchWithQueryParameters() {
        final Form form = getQuery();
        extractLanguageFromQueryParameters(form);
        setUpSearchForLimitParameter(form);
        setUpSearchForSkipParameter(form);
        setUpSearchForFromIdParameter(form);
        relations = relationSearch.withSort("weight", Search.REVERSE_ORDER).execute();
    }

    private void extractLanguageFromQueryParameters(final Form form) {
        if (form.getQueryString().contains("languageCode")) {
            steambeatLanguage = SteambeatLanguage.forString(form.getFirstValue("languageCode").trim());
        } else {
            steambeatLanguage = SteambeatLanguage.none();
        }
    }

    private void setUpSearchForLimitParameter(final Form form) {
        if (form.getQueryString().contains("limit")) {
            final int limit = Integer.parseInt(form.getFirstValue("limit").trim());
            if (limit > 100) {
                throw new SteambeatJsonException();
            }
            relationSearch.withLimit(limit);
        } else {
            relationSearch.withLimit(100);
        }
    }

    private void setUpSearchForSkipParameter(final Form form) {
        if (form.getQueryString().contains("skip")) {
            relationSearch.withSkip(Integer.parseInt(form.getFirstValue("skip").trim()));
        } else {
            relationSearch.withSkip(0);
        }
    }

    private void setUpSearchForFromIdParameter(final Form form) {
        if (form.getQueryString().contains("referenceId")) {
            relationSearch.withFrom(UUID.fromString(form.getFirstValue("referenceId").trim()));
        }
    }

    public void getReferenceDataForEachRelation() {
        for (final Relation relation : relations) {
            addReferenceData(relation);
        }
    }

    private void addReferenceData(final Relation relation) {
        final Keyword keyword = keywordService.lookUp(relation.getToId(), steambeatLanguage);
        final ReferenceData referenceData = referenceDataFactory.getReferenceData(relation.getToId(), keyword);
        referenceDataList.add(referenceData);
    }

    private final RelationSearch relationSearch;
    private final KeywordService keywordService;
    private final ReferenceDataFactory referenceDataFactory;
    private List<Relation> relations = Lists.newArrayList();
    private SteambeatLanguage steambeatLanguage;
    private final List<ReferenceData> referenceDataList = Lists.newArrayList();
}
