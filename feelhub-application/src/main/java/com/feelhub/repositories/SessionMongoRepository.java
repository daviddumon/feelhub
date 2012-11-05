package com.feelhub.repositories;

import com.feelhub.domain.session.*;
import com.feelhub.domain.user.User;
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
        criteria.add(Restrictions.equals("idUser", user.getId()));
        return criteria.list();
    }
}
