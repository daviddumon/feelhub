package com.steambeat.web.resources;

import com.steambeat.repositories.Repositories;
import com.steambeat.domain.subject.feed.*;
import com.steambeat.web.SteambeatTemplateRepresentation;
import com.steambeat.domain.statistics.*;
import org.apache.log4j.Logger;
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
    private static final Logger logger = Logger.getLogger(HomeResource.class);
}




