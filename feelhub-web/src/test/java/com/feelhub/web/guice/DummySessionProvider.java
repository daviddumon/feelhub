package com.feelhub.web.guice;

import com.feelhub.repositories.SessionProvider;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.Criteria;

import static org.mockito.Mockito.*;

public class DummySessionProvider extends SessionProvider {

    @Override
    public MongoSession get() {
        final MongoSession session = mock(MongoSession.class);
        when(session.createCriteria(any(Class.class))).thenReturn(mock(Criteria.class));
        return session;
    }


}
