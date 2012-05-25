package com.steambeat.web.migration;

import com.steambeat.application.SubjectService;
import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.subject.*;
import com.steambeat.domain.subject.concept.ConceptFactory;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.domain.subject.webpage.WebPageFactory;
import com.steambeat.repositories.*;
import com.steambeat.web.migration.fake.*;

import java.util.List;

public class Migration00002 extends Migration {

    public Migration00002(final SessionProvider sessionProvider) {
        super(sessionProvider, 2);
    }

    @Override
    protected void doRun() {
        final SubjectService subjectService = new SubjectService(new SubjectFactory(new WebPageFactory(new MigrationUriScraper(), new MigrationAlchemyEntityAnalyzer()), new ConceptFactory(new BingLink())));
        Repositories.initialize(new MongoRepositories(provider));
        final Steam steam = Repositories.subjects().getSteam();
        final List<Subject> subjects = Repositories.subjects().getAll();
        logger.warn("Found " + subjects.size() + " subjects to migrate");
        int count = 1;
        for (final Subject subject : subjects) {
            logger.warn("Looking up subject " + subject.getId());
            if (subject.isExpired() && !subject.equals(steam)) {
                logger.warn("count : " + count++);
                subjectService.lookUpWebPage(subject.getId());
            }
        }
    }
}
