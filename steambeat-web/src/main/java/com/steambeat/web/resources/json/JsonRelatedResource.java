package com.steambeat.web.resources.json;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.illustration.Illustration;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.relation.Relation;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.web.dto.ReferenceData;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import com.steambeat.web.search.*;
import org.restlet.data.*;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.*;

public class JsonRelatedResource extends ServerResource {

    @Inject
    public JsonRelatedResource(final RelationSearch relationSearch, final KeywordService keywordService) {
        this.relationSearch = relationSearch;
        this.keywordService = keywordService;
    }

    @Get
    public Representation get() {
        doSearchWithQueryParameters();
        getReferenceDataForEachRelation();
        return SteambeatTemplateRepresentation.createNew("json/related.json.ftl", getContext(), MediaType.APPLICATION_JSON, getRequest()).with("referenceDataList", referenceDataList);
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
        for (Relation relation : relations) {
            addReferenceData(relation);
        }
    }

    private void addReferenceData(final Relation relation) {
        ReferenceData.Builder builder = new ReferenceData.Builder();
        setReferenceId(relation, builder);
        setKeywordAndLanguage(relation, builder);
        setIllustration(relation, builder);
        referenceDataList.add(builder.build());
    }

    private void setReferenceId(final Relation relation, final ReferenceData.Builder builder) {
        builder.referenceId(relation.getToId());
    }

    private void setKeywordAndLanguage(final Relation relation, final ReferenceData.Builder builder) {
        Keyword keyword;
        final List<Keyword> keywords = keywordService.lookUpAll(relation.getToId());
        if (!keywords.isEmpty()) {
            keyword = getGoodKeyword(keywords);
        } else {
            keyword = new Keyword("?", steambeatLanguage, relation.getToId());
        }
        builder.keyword(keyword);
        builder.language(keyword.getLanguage());
    }

    private Keyword getGoodKeyword(final List<Keyword> keywords) {
        Keyword referenceKeyword = null;
        for (Keyword keyword : keywords) {
            if (keyword.getLanguage().equals(steambeatLanguage)) {
                return keyword;
            } else if (keyword.getLanguage().equals(SteambeatLanguage.reference())) {
                referenceKeyword = keyword;
            }
        }
        if (referenceKeyword != null) {
            return referenceKeyword;
        } else {
            return keywords.get(0);
        }
    }

    private void setIllustration(final Relation relation, final ReferenceData.Builder builder) {
        final List<Illustration> illustrations = Repositories.illustrations().forReferenceId(relation.getToId());
        if (!illustrations.isEmpty()) {
            builder.illustration(illustrations.get(0));
        }
    }

    private RelationSearch relationSearch;
    private KeywordService keywordService;
    private List<Relation> relations = Lists.newArrayList();
    private SteambeatLanguage steambeatLanguage;
    private List<ReferenceData> referenceDataList = Lists.newArrayList();
}
