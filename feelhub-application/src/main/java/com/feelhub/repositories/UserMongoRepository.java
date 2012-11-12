package com.feelhub.repositories;

import com.feelhub.domain.user.User;
import com.feelhub.domain.user.UserRepository;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.Criteria;
import org.mongolink.domain.criteria.Restrictions;

public class UserMongoRepository extends BaseMongoRepository<User> implements UserRepository {

    public UserMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public User forEmail(final String email) {
        final Criteria criteria = createCriteria();
        criteria.add(Restrictions.equals("email", email));
        return extractOne(criteria);
    }

}
