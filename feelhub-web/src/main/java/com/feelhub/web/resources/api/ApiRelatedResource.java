package com.feelhub.web.resources.api;

import com.feelhub.application.KeywordService;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.*;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class ApiRelatedResource extends ServerResource {

    @Inject
    public ApiRelatedResource(final RelationSearch relationSearch, final KeywordService keywordService, final KeywordDataFactory keywordDataFactory) {
        this.relationSearch = relationSearch;
        this.keywordService = keywordService;
        this.keywordDataFactory = keywordDataFactory;
    }

    @Get
    public ModelAndView represent() {
        doSearchWithQueryParameters();
        getKeywordDataForEachRelation();
        return ModelAndView.createNew("api/related.json.ftl", MediaType.APPLICATION_JSON).with("keywordDataList", keywordDataList);
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
            feelhubLanguage = FeelhubLanguage.forString(form.getFirstValue("languageCode").trim());
        } else {
            feelhubLanguage = FeelhubLanguage.none();
        }
    }

    private void setUpSearchForLimitParameter(final Form form) {
        if (form.getQueryString().contains("limit")) {
            final int limit = Integer.parseInt(form.getFirstValue("limit").trim());
            if (limit > 100) {
                throw new FeelhubApiException();
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
        if (form.getQueryString().contains("topicId")) {
            relationSearch.withFrom(UUID.fromString(form.getFirstValue("topicId").trim()));
        }
    }

    public void getKeywordDataForEachRelation() {
        for (final Relation relation : relations) {
            addKeywordData(relation);
        }
    }

    private void addKeywordData(final Relation relation) {
        final Keyword keyword = keywordService.lookUp(relation.getToId(), feelhubLanguage);
        final KeywordData keywordData = keywordDataFactory.getKeywordData(relation.getToId(), keyword);
        keywordDataList.add(keywordData);
    }

    private final RelationSearch relationSearch;
    private final KeywordService keywordService;
    private final KeywordDataFactory keywordDataFactory;
    private List<Relation> relations = Lists.newArrayList();
    private FeelhubLanguage feelhubLanguage;
    private final List<KeywordData> keywordDataList = Lists.newArrayList();
}
