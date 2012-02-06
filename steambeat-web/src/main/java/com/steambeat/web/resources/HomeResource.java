package com.steambeat.web.resources;

import com.steambeat.web.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class HomeResource extends ServerResource {

    @Get
    public Representation represent() {
        //todo clean
        //final WebPage steambeat = new WebPage(new Association(new Uri("steambeat"), null));
        //statistics = Repositories.statistics().forSubject(steambeat, Granularity.all);
        //for (final Statistics statistic : statistics) {
        //    counter = statistic.getBadOpinions() + statistic.getGoodOpinions();
        //}
        return SteambeatTemplateRepresentation.createNew("home.ftl", getContext()).with("counter", 0);
    }

    //private List<Statistics> statistics;
    //private int counter;
}




