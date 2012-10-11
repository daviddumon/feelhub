package com.steambeat.web.resources.json;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import org.json.*;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.*;

import java.util.List;

public class JsonCreateOpinionResource extends ServerResource {

    @Inject
    public JsonCreateOpinionResource(final OpinionService opinionService, final KeywordService keywordService) {
        this.opinionService = opinionService;
        this.keywordService = keywordService;
    }

    @Post
    public void post(JsonRepresentation jsonRepresentation) {
        try {
            final JSONObject jsonOpinion = jsonRepresentation.getJsonObject();
            getKeywordsAndJudgments(jsonOpinion);
            opinionService.addOpinion(text, judgments);
            setStatus(Status.SUCCESS_CREATED);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(jsonRepresentation);
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private void getKeywordsAndJudgments(final JSONObject jsonOpinion) throws JSONException {
        text = jsonOpinion.get("text").toString();
        final JSONArray jsonJudgments = jsonOpinion.getJSONArray("judgments");
        for (int i = 0; i < jsonJudgments.length(); i++) {
            final JSONObject judgment = jsonJudgments.getJSONObject(i);
            final Feeling feeling = Feeling.valueOf(judgment.get("feeling").toString());
            final Keyword keyword = getKeywordFromJudgment(judgment);
            judgments.add(new Judgment(keyword.getReferenceId(), feeling));
        }
    }

    private Keyword getKeywordFromJudgment(final JSONObject judgment) throws JSONException {
        final String keywordValue = judgment.get("keywordValue").toString();
        final String languageCode = judgment.get("languageCode").toString();
        return getKeywordOrCreate(keywordValue, SteambeatLanguage.forString(languageCode));
    }

    public Keyword getKeywordOrCreate(final String value, final SteambeatLanguage steambeatLanguage) {
        final Keyword keyword;
        try {
            keyword = keywordService.lookUp(value, steambeatLanguage);
        } catch (KeywordNotFound e) {
            return keywordService.createKeyword(value, steambeatLanguage);
        }
        return keyword;
    }

    private String text;
    List<Judgment> judgments = Lists.newArrayList();
    private OpinionService opinionService;
    private KeywordService keywordService;
}
