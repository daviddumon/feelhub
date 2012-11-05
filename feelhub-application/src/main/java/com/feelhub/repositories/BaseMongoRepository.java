package com.feelhub.repositories;

import com.feelhub.domain.*;
import com.feelhub.domain.user.User;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.Criteria;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseMongoRepository<T extends Entity> implements Repository<T> {

    public BaseMongoRepository(final MongoSession mongoSession) {
        session = mongoSession;
    }

    @Override
    public List<T> getAll() {
        return session.getAll(getPersistentType());
    }

    @Override
    public void add(final T element) {
        session.save(element);
    }

    @Override
    public void clear() {
    }

    @Override
    public T get(final Object id) {
        return session.get(id, getPersistentType());
    }

    @Override
    public boolean exists(final Object uri) {
        return false;
    }

    @Override
    public void delete(final T element) {
        session.delete(element);
    }

    protected final Class<T> getPersistentType() {
        final ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) superclass.getActualTypeArguments()[0];
    }

    protected MongoSession getSession() {
        return session;
    }

	protected Criteria<T> createCriteria() {
		return getSession().createCriteria(getPersistentType());
	}

	protected T extractOne(final Criteria criteria) {
		final List<User> results = criteria.list();
		if (results.isEmpty()) {
			return null;
		} else {
			return (T) results.get(0);
		}
	}

	protected MongoSession session;
}
