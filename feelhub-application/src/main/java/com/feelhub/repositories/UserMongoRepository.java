package com.feelhub.repositories;

import com.feelhub.domain.user.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class UserMongoRepository extends BaseMongoRepository<User> implements UserRepository {

    public UserMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public User get(final String id) {
        return get(UUID.fromString(id));
    }

    @Override
    public User forEmail(final String email) {
        final Criteria criteria = createCriteria();
        criteria.add(Restrictions.equals("email", email));
        return extractOne(criteria);
    }

    @Override
    public User findBySocialNetwork(final SocialNetwork socialNetwork, final String id) {
        final Criteria<User> criteria = createCriteria();
        criteria.add(Restrictions.elementMatch("socialAuths").equals("network", socialNetwork).equals("id", id));
        final List<User> list = criteria.list();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

}
