package com.steambeat.web.migration;

import com.steambeat.application.AssociationService;
import com.steambeat.domain.association.uri.UriPathResolver;
import com.steambeat.domain.relation.alchemy.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.*;

import java.util.List;

public class Migration00004 extends Migration {

    public Migration00004(final SessionProvider provider) {
        super(provider, 4);
    }

    @Override
    protected void doRun() {
        System.out.println("Migration 0004 en cours");
        final List<WebPage> webPages = Repositories.subjects().getAllWebPages();
        System.out.println("webpages: " + webPages.size());
        int i = 0;
        for (WebPage webpage : webPages) {
            final AlchemyEntityAnalyzer alchemyEntityAnalyzer = new AlchemyEntityAnalyzer(new AlchemyJsonEntityProvider(new AlchemyLink()), new AssociationService(new UriPathResolver()));
            System.out.println("webpage " + i++ + " " + webpage.getUri());
            try {
                alchemyEntityAnalyzer.analyze(webpage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}