package com.steambeat.web.migration;

import com.google.common.collect.Lists;
import com.mongodb.*;
import com.steambeat.repositories.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsJavaMigration extends TestWithMongoRepository {

    @Test
    public void canRun() {
        final Migration migration = new FakeMigration(getProvider(), 1);

        migration.run();

        final DBCollection migrationCollection = mongo.getCollection("migration");
        final DBObject migrationFound = migrationCollection.findOne();
        assertThat(migrationFound.get("_id"), notNullValue());
        assertThat(migrationFound.get("creationDate"), notNullValue());
        assertThat((Integer) migrationFound.get("number"), is(1));
    }

    @Test
    @Ignore("FakeDBCollection not implementing field filter")
    public void doNotRunIfAlreadyMigrate() {
        final Migration migration = new FakeMigration(getProvider(), 1);
        final Migration anotherMigration = new FakeMigration(getProvider(), 2);

        migration.run();
        migration.run();
        migration.run();
        anotherMigration.run();

        List<Object> results = Lists.newArrayList();
        final DBCollection migrationCollection = mongo.getCollection("migration");
        final DBCursor migrationsFound = migrationCollection.find();
        while (migrationsFound.hasNext()) {
            results.add(migrationsFound.next());
        }
        migrationsFound.close();
        assertThat(results.size(), is(2));
    }

    private class FakeMigration extends Migration {

        public FakeMigration(final SessionProvider provider, final int number) {
            super(provider, number);
        }

        @Override
        protected void doRun() {

        }
    }
}
