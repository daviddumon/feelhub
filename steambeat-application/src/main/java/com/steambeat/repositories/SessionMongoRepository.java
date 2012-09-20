package com.steambeat.repositories;

import com.steambeat.domain.session.*;
import com.steambeat.domain.user.User;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.List;

public class SessionMongoRepository extends BaseMongoRepository<Session> implements SessionRepository {

    public SessionMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<Session> forUser(final User user) {
        final Criteria criteria = getSession().createCriteria(Session.class);
        criteria.add(Restrictions.equals("email", user.getEmail()));
        return criteria.list();
    }
}
