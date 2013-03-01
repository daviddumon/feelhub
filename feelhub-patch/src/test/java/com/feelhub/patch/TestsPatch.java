package com.feelhub.patch;

import com.feelhub.repositories.*;
import org.junit.*;
import org.mongolink.test.FongoDbFactory;

import java.sql.SQLException;

import static org.fest.assertions.Assertions.*;

public class TestsPatch {

    @Before
    public void before() throws SQLException {
        patch = new FakePatch(new FakeSessionProvider());
    }

    @After
    public void after() {
        FongoDbFactory.clean();
    }

    @Test
    public void canExecuteBusinessPatch() throws SQLException {
        patch.withBusinessPatch = true;

        patch.execute();

        openSession();
        assertThat(Repositories.users().getAll()).hasSize(1);
    }

    @Test
    public void cantExecuteBusinessPatch() throws SQLException {
        patch.withBusinessPatch = false;

        patch.execute();

        openSession();
        assertThat(Repositories.users().getAll()).isEmpty();
    }

    private void openSession() {
        final FakeSessionProvider provider = new FakeSessionProvider();
        provider.init();
        provider.start();
        Repositories.initialize(new MongoRepositories(provider));
    }

    private FakePatch patch;
}

