package com.steambeat.web.migration;

import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.*;

import java.util.List;

public class Migration00003 extends Migration {

    public Migration00003(final SessionProvider provider) {
        super(provider, 3);
    }

    @Override
    protected void doRun() {
        final List<Subject> subjects = Repositories.subjects().getAll();
        for (final Subject subject : subjects) {
            subject.setLastModificationDate(subject.getCreationDate());
        }
    }
}
