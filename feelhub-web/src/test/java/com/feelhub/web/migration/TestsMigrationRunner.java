package com.feelhub.web.migration;

import com.feelhub.repositories.TestWithMongoRepository;
import org.junit.Test;
import org.restlet.Context;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsMigrationRunner extends TestWithMongoRepository {

    @Test
    public void canChangeContextReadyStatus() {
        final Context context = new Context();
        context.getAttributes().put("com.feelhub.ready", false);
        final MigrationRunner migrationRunner = new MigrationRunner(getProvider());
        migrationRunner.setContext(context);

        migrationRunner.run();

        assertThat((Boolean) context.getAttributes().get("com.feelhub.ready"), is(true));
    }
}
