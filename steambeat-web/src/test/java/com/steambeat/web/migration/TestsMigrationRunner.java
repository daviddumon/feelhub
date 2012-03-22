package com.steambeat.web.migration;

import com.steambeat.repositories.TestWithMongoRepository;
import org.junit.Test;
import org.restlet.Context;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsMigrationRunner extends TestWithMongoRepository {

    @Test
    public void canChangeContextReadyStatus() {
        final Context context = new Context();
        context.getAttributes().put("com.steambeat.ready", false);
        final MigrationRunner migrationRunner = new MigrationRunner(getProvider());
        migrationRunner.setContext(context);

        migrationRunner.run();

        assertThat((Boolean) context.getAttributes().get("com.steambeat.ready"), is(true));
    }
}