package com.steambeat.repositories;

import com.steambeat.domain.subject.*;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.domain.subject.webpage.WebPage;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.List;

@SuppressWarnings("unchecked")
public class SubjectMongoRepository extends BaseMongoRepository<Subject> implements SubjectRepository {

    public SubjectMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Steam getSteam() {
        final Criteria criteria = session.createCriteria(Subject.class);
        criteria.add(Restrictions.equals("__discriminator", "Steam"));
        final List results = criteria.list();
        if (results.isEmpty()) {
            return null;
        }
        return (Steam) results.get(0);
    }

    @Override
    public List<WebPage> getAllWebPages() {
        final Criteria criteria = session.createCriteria(Subject.class);
        criteria.add(Restrictions.equals("__discriminator", "WebPage"));
        return criteria.list();
    }
}
