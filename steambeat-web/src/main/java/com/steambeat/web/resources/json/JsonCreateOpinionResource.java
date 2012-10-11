package com.steambeat.web.resources.json;

import com.steambeat.domain.opinion.Feeling;
import org.json.*;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.*;

public class JsonCreateOpinionResource extends ServerResource {

    @Post
    public void post(JsonRepresentation jsonRepresentation) {
        try {
            final JSONObject jsonOpinion = jsonRepresentation.getJsonObject();
            final String text = extractText(jsonOpinion);
            final Feeling feeling = extractFeeling(jsonOpinion);
            final String keywordValue = extractKeywordValue(jsonOpinion);
            final String languageCode = extractLanguageCode(jsonOpinion);
            final String userLanguageCode = extractUserLanguageCode(jsonOpinion);

            setStatus(Status.SUCCESS_CREATED);
        } catch (JSONException e) {
            e.printStackTrace();
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private String extractText(final JSONObject jsonOpinion) throws JSONException {
        return jsonOpinion.get("text").toString();
    }

    private Feeling extractFeeling(final JSONObject jsonOpinion) throws JSONException {
        return Feeling.valueOf(jsonOpinion.get("feeling").toString());
    }

    private String extractKeywordValue(final JSONObject jsonOpinion) throws JSONException {
        return jsonOpinion.get("keywordValue").toString();
    }

    private String extractLanguageCode(final JSONObject jsonOpinion) throws JSONException {
        return jsonOpinion.get("languageCode").toString();
    }

    private String extractUserLanguageCode(final JSONObject jsonOpinion) throws JSONException {
        return jsonOpinion.get("userLanguageCode").toString();
    }
}
