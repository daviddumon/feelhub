package com.feelhub.repositories;

import com.feelhub.domain.user.SocialNetwork;
import com.feelhub.domain.user.User;
import com.feelhub.domain.user.UserRepository;
import com.google.common.base.Optional;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.Criteria;
import org.mongolink.domain.criteria.Restrictions;

import java.util.List;
import java.util.UUID;

public class UserMongoRepository extends BaseMongoRepository<User> implements UserRepository {

    public UserMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public User get(final String id) {
        return get(UUID.fromString(id));
    }

    @Override
    public Optional<User> forEmail(final String email) {
        final Criteria criteria = createCriteria();
        criteria.add(Restrictions.equals("email", email));
        return Optional.fromNullable(extractOne(criteria));
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
