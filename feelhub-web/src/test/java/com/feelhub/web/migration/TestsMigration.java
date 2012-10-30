package com.feelhub.web.migration;

import com.feelhub.repositories.*;
import com.mongodb.*;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsMigration extends TestWithMongoRepository {

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
    public void canRunForMigration2() {
        final Migration migration = new FakeMigration(getProvider(), 2);

        migration.run();

        final DBCollection migrationCollection = mongo.getCollection("migration");
        final DBObject migrationFound = migrationCollection.findOne();
        assertThat(migrationFound.get("_id"), notNullValue());
        assertThat(migrationFound.get("creationDate"), notNullValue());
        assertThat((Integer) migrationFound.get("number"), is(2));
    }

    @Test
    public void doNotRunIfAlreadyRunned() {
        final Migration migration = new FakeMigration(getProvider(), 3);

        migration.run();
        migration.run();

        final DBCollection migrationCollection = mongo.getCollection("migration");
        final DBCursor migrations = migrationCollection.find();
        assertThat(migrations.itcount(), is(1));
        migrations.close();
    }

    private class FakeMigration extends Migration {

        public FakeMigration(final SessionProvider provider, final int number) {
            super(provider, number);
        }

        @Override
        public void run() {
            logger.warn("MIGRATION - MIGRATION " + number);
            if (canRun()) {
                endOfMigration();
            }
            logger.warn("MIGRATION - END OF MIGRATION " + number);
        }

        @Override
        protected void doRun() {

        }
    }
}
