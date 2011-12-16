package com.steambeat.web.tools;

import com.steambeat.domain.opinion.Opinion;
import org.json.*;

public class JsonExtractor {

    //todo : on n'a pas prouver que l'extract etait correct
    public static JSONObject extract(final Opinion opinion) throws JSONException {
        final JSONObject json = new JSONObject();
        json.put("text", opinion.getText());
        json.put("subjectId", opinion.getJudgments().get(0).getSubjectId());
        json.put("feeling", opinion.getJudgments().get(0).getFeeling());
        return json;
    }
}
