package com.steambeat.web.migration;

import com.steambeat.application.SubjectService;
import com.steambeat.domain.subject.*;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.*;

import java.util.List;

public class Migration00002 extends Migration {

    public Migration00002(final SessionProvider sessionProvider) {
        super(sessionProvider, 2);
    }

    @Override
    protected void doRun() {
        final SubjectService subjectService = new SubjectService(new SubjectFactory(new WebPageFactory()));
        Repositories.initialize(new MongoRepositories(provider));
        final Steam steam = Repositories.subjects().getSteam();
        final List<Subject> subjects = Repositories.subjects().getAll();
        logger.info("Found " + subjects.size() + " subjects to migrate");
        int count = 1;
        for (Subject subject : subjects) {
            logger.info("Looking up subject " + subject.getId());
            if (subject.isExpired() && !subject.equals(steam)) {
                subjectService.lookUpWebPage(subject.getId());
            }
            logger.info("count : " + count++);
        }
    }
}
