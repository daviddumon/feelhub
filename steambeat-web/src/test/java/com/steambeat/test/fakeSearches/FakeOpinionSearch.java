package com.steambeat.test.fakeSearches;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;
import com.steambeat.web.search.OpinionSearch;
import org.joda.time.Interval;

import java.util.List;

public class FakeOpinionSearch extends OpinionSearch {

    public FakeOpinionSearch() {
        super(null);
    }

    @Override
    public List<Opinion> last() {
        return Repositories.opinions().getAll();
    }

    @Override
    public List<Opinion> forSubject(final Subject subject) {
        return Lists.newArrayList(Iterables.filter(Repositories.opinions().getAll(), perSubject(subject)));
    }

    private Predicate<Opinion> perSubject(final Subject subject) {
        return new Predicate<Opinion>() {
            @Override
            public boolean apply(Opinion opinion) {
                return opinion.getSubject().equals(subject);
            }
        };
    }

    @Override
    public List<Opinion> forInterval(final Interval interval) {
        return null;
    }

    public List<Opinion> forIntervalSubjectSkipAndLimit(final Interval interval, final Subject subject, final int skipNumber, final int limitNumber) {
        return null;
    }
}
