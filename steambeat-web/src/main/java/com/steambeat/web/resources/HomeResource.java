package com.steambeat.web.resources;

import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.feed.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.web.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.List;

public class HomeResource extends ServerResource {

    @Get
    public Representation represent() {
        final Feed steambeat = new Feed(new Association(new Uri("steambeat"), null));
        statistics = Repositories.statistics().forFeed(steambeat, Granularity.all);
        for (Statistics statistic : statistics) {
            counter = statistic.getBadOpinions() + statistic.getGoodOpinions() + statistic.getNeutralOpinions();
        }
        return SteambeatTemplateRepresentation.createNew("home.ftl", getContext()).with("counter", counter);
    }

    private List<Statistics> statistics;
    private int counter;
}




