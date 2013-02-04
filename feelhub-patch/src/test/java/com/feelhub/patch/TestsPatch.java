package com.feelhub.patch;

import com.feelhub.repositories.MongoRepositories;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.SessionProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.fest.assertions.Assertions.assertThat;

public class TestsPatch {

    @Before
	public void before() throws SQLException {
        patch = new FakePatch(new FakeSessionProvider());
	}

    @After
    public void after() {
        FongoDBFactory.clean();
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
        FakeSessionProvider provider = new FakeSessionProvider();
        provider.init();
        provider.start();
        Repositories.initialize(new MongoRepositories(provider));
    }

    private FakePatch patch;
}

