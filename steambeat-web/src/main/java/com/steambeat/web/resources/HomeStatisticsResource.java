package com.steambeat.web.resources;

import com.steambeat.domain.statistics.*;
import org.joda.time.DateTime;
import org.json.*;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.List;

public class HomeStatisticsResource extends ServerResource {

    @Override
    protected void doInit() throws ResourceException {
        //final WebPage steambeat = new WebPage(new Association(new Uri("steambeat"), null));
        granularity = Granularity.valueOf(getRequestAttributes().get("granularity").toString());
        final DateTime start = new DateTime(Long.parseLong(getRequestAttributes().get("start").toString()));
        final DateTime end = new DateTime(Long.parseLong(getRequestAttributes().get("end").toString()));
        //statistics = Repositories.statistics().forSubject(steambeat, granularity, granularity.intervalFor(start, end));
    }

    @Get
    public Representation represent() throws JSONException {
        final JSONObject result = new JSONObject();
        result.put("granularity", granularity);
        final JSONArray stats = new JSONArray();
        for (final Statistics statistic : statistics) {
            stats.put(jsonFor(statistic));
        }
        result.put("stats", stats);
        return new JsonRepresentation(result);
    }

    private JSONObject jsonFor(final Statistics stat) throws JSONException {
        final JSONObject result = new JSONObject();
        result.put("time", stat.getDate().getMillis());
        final JSONObject opinions = new JSONObject();
        opinions.put("good", stat.getGoodJudgments());
        opinions.put("bad", stat.getBadJudgments());
        result.put("opinions", opinions);
        return result;
    }

    private List<Statistics> statistics;
    private Granularity granularity;
}
