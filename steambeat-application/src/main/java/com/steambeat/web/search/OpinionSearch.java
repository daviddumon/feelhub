package com.steambeat.web.search;

import com.google.common.collect.Lists;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.SessionProvider;
import fr.bodysplash.mongolink.MongoSession;
import fr.bodysplash.mongolink.domain.criteria.*;
import org.joda.time.Interval;

import javax.inject.Inject;
import java.util.List;

public class OpinionSearch {

    @Inject
    public OpinionSearch(SessionProvider provider) {
        this.provider = provider;
    }

    public List<Opinion> last() {
        final MongoSession session = provider.get();
        final Criteria criteria = session.createCriteria(Opinion.class);
        criteria.limit(50);
        return criteria.list();
    }

    public List<Opinion> forInterval(Interval interval) {
        final MongoSession session = provider.get();
        final Criteria criteria = session.createCriteria(Opinion.class);
        criteria.add(Restrictions.between("creationDate", interval.getStartMillis(), interval.getEndMillis()));
        return criteria.list();
    }

    public List<Opinion> forSubject(final Subject subject) {
        final MongoSession session = provider.get();
        final Criteria criteria = session.createCriteria(Opinion.class);
        criteria.add(Restrictions.eq("subjectId", subject.getId()));
        return criteria.list();
    }

    public List<Opinion> forIntervalSubjectSkipAndLimit(final Interval interval, final Subject subject, final int skipNumber, final int limitNumber) {
        //final Criteria criteria = session.createCriteria(Opinion.class, skipNumber, limitNumber);
        //criteria.add(Restrictions.between("creationDate", interval.getStartMillis(), interval.getEndMillis()));
        //criteria.add(Restrictions.eq("subject", subject.getId()));
        //return criteria.list();

//        final Criteria criteria = session.createCriteria(Opinion.class);
//        criteria.add(Restrictions.eq("subject", subject));
//        return criteria.list();
        return Lists.newArrayList();
    }

    private SessionProvider provider;
}
