package com.steambeat.repositories;

import com.steambeat.domain.*;
import org.mongolink.MongoSession;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings("unchecked")
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
        return session.get(id.toString(), getPersistentType());
    }

    @Override
    public boolean exists(final Object uri) {
        return false;
    }

    protected final Class<T> getPersistentType() {
        final ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) superclass.getActualTypeArguments()[0];
    }

    protected MongoSession getSession() {
        return session;
    }

    protected MongoSession session;
}
