package com.steambeat.web.tools;

import com.steambeat.domain.opinion.Opinion;
import org.json.*;

public class JsonExtractor {

    public static JSONObject extract(final Opinion opinion) throws JSONException {
        final JSONObject json = new JSONObject();
        json.put("subjectId", opinion.getSubjectId());
        json.put("text", opinion.getText());
        json.put("feeling", opinion.getFeeling());
        return json;
    }
}
