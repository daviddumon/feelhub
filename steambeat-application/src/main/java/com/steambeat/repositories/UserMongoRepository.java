package com.steambeat.repositories;

import com.steambeat.domain.user.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

@SuppressWarnings("unchecked")
public class UserMongoRepository extends BaseMongoRepository<User> implements UserRepository{

    public UserMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public User forSecret(final UUID secret) {
        final Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.equals("secret", secret.toString()));
        final List<User> results = criteria.list();
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }
}
