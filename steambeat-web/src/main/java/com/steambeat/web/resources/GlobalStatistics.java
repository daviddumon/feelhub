package com.steambeat.web.resources;

import com.steambeat.repositories.Repositories;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.feed.*;
import org.joda.time.DateTime;
import org.json.*;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.List;

public class GlobalStatistics extends ServerResource {

    @Override
    protected void doInit() throws ResourceException {
        Feed steambeat = new Feed(new Association(new Uri("steambeat"), null));
        granularity = Granularity.valueOf(getRequestAttributes().get("granularity").toString());
        DateTime start = new DateTime(Long.parseLong(getRequestAttributes().get("start").toString()));
        DateTime end = new DateTime(Long.parseLong(getRequestAttributes().get("end").toString()));
        statistics = Repositories.statistics().forFeed(steambeat, granularity, granularity.intervalFor(start, end));
    }

    @Get
    public Representation represent() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("granularity", granularity);
        final JSONArray stats = new JSONArray();
        for (Statistics statistic : statistics) {
            stats.put(jsonFor(statistic));
        }
        result.put("stats", stats);
        return new JsonRepresentation(result);
    }

    private JSONObject jsonFor(Statistics stat) throws JSONException {
        final JSONObject result = new JSONObject();
        result.put("time", stat.getDate().getMillis());
        final JSONObject opinions = new JSONObject();
        opinions.put("good", stat.getGoodOpinions());
        opinions.put("bad", stat.getBadOpinions());
        opinions.put("neutral", stat.getNeutralOpinions());
        result.put("opinions", opinions);
        return result;
    }

    private List<Statistics> statistics;
    private Granularity granularity;
}
