package com.steambeat.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsBaseEntity {

    @Test
    public void canTestEquality() {
        final FakeEntity entity1 = createEntity(1);
        final FakeEntity entity2 = createEntity(1);

        final boolean equals = entity1.equals(entity2);

        assertThat(equals, is(true));
    }

    @Test
    public void canTestInequality() {
        final FakeEntity entity1 = createEntity(1);
        final FakeEntity entity2 = createEntity(2);

        final boolean equals = entity1.equals(entity2);

        assertThat(equals, is(false));
    }

    @Test
    public void canTestEqualityWithInheritance() {
        final FakeEntity entity1 = createEntity(1);
        final OtherFakeEntity entity2 = createOtherFakeEntity(1);

        final boolean equals = entity1.equals(entity2);

        assertThat(equals, is(false));
    }

    @Test
    public void canGiveHashCode() {
        final FakeEntity entity1 = createEntity(1);
        final FakeEntity entity2 = createEntity(1);

        final boolean equals = entity1.hashCode() == entity2.hashCode();

        assertThat(equals, is(true));
    }

    @Test
    public void hashCodeIsAlsoBaseOnType() {
        final FakeEntity entity1 = createEntity(1);
        final OtherFakeEntity entity2 = createOtherFakeEntity(2);

        final boolean equals = entity1.hashCode() == entity2.hashCode();

        assertThat(equals, is(false));
    }

    private OtherFakeEntity createOtherFakeEntity(final int id) {
        final OtherFakeEntity entity2 = new OtherFakeEntity();
        entity2.id = id;
        return entity2;
    }

    private FakeEntity createEntity(final int id) {
        final FakeEntity entity1 = new FakeEntity();
        entity1.id = id;
        return entity1;
    }

    private static class FakeEntity extends BaseEntity {

        @Override
        public Object getId() {
            return id;
        }

        private int id;
    }

    private static class OtherFakeEntity extends BaseEntity {
        @Override
        public Object getId() {
            return id;
        }

        private int id;
    }
}
