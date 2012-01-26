package com.steambeat.web.resources;

import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.web.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.List;

public class HomeResource extends ServerResource {

    @Get
    public Representation represent() {
        final WebPage steambeat = new WebPage(new Association(new Uri("steambeat"), null));
        statistics = Repositories.statistics().forSubject(steambeat, Granularity.all);
        for (final Statistics statistic : statistics) {
            counter = statistic.getBadOpinions() + statistic.getGoodOpinions() + statistic.getNeutralOpinions();
        }
        return SteambeatTemplateRepresentation.createNew("home.ftl", getContext()).with("counter", counter);
    }

    private List<Statistics> statistics;
    private int counter;
}




