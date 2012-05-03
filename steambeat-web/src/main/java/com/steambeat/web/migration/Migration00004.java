package com.steambeat.web.migration;

import com.steambeat.application.AssociationService;
import com.steambeat.domain.association.uri.UriPathResolver;
import com.steambeat.domain.relation.alchemy.*;
import com.steambeat.domain.subject.Subject;
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
        final List<Subject> webPages = Repositories.subjects().getAll();
        System.out.println("subjects: " + webPages.size());
        int i = 0;
        final AlchemyEntityAnalyzer alchemyEntityAnalyzer = new AlchemyEntityAnalyzer(new AlchemyJsonEntityProvider(new AlchemyLink()), new AssociationService(new UriPathResolver()));
        for (Subject subject : webPages) {
            final WebPage webPage = (WebPage) subject;
            System.out.println("webpage " + i++ + " " + webPage.getUri());
            try {
                alchemyEntityAnalyzer.analyze(webPage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
