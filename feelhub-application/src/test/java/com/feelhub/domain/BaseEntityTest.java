package com.feelhub.domain;

import com.feelhub.test.SystemTime;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class BaseEntityTest {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canTestEquality() {
        final FakeEntity entity1 = createEntity(1);
        final FakeEntity entity2 = createEntity(1);

        final boolean equals = entity1.equals(entity2);

        assertThat(equals).isTrue();
    }

    @Test
    public void canTestInequality() {
        final FakeEntity entity1 = createEntity(1);
        final FakeEntity entity2 = createEntity(2);

        final boolean equals = entity1.equals(entity2);

        assertThat(equals).isFalse();
    }

    @Test
    public void canTestEqualityBetweenSubclasses() {
        final FakeEntity entity1 = createEntity(1);
        final OtherFakeEntity entity2 = createOtherFakeEntity(1);

        final boolean equals = entity1.equals(entity2);

        assertThat(equals).isFalse();
    }

    @Test
    public void canGiveHashCode() {
        final FakeEntity entity1 = createEntity(1);
        final FakeEntity entity2 = createEntity(1);

        final boolean result = entity1.hashCode() == entity2.hashCode();

        assertThat(result).isTrue();
    }

    @Test
    public void hashCodeIsNotBaseOnType() {
        final FakeEntity entity1 = createEntity(1);
        final OtherFakeEntity entity2 = createOtherFakeEntity(1);

        final boolean result = entity1.hashCode() == entity2.hashCode();

        assertThat(result).isTrue();
    }

    @Test
    public void hasCreationDate() {
        final FakeEntity entity = createEntity(1);

        assertThat(entity.getCreationDate()).isEqualTo(time.getNow());
    }

    @Test
    public void hasModificationDate() {
        final FakeEntity entity = createEntity(1);

        assertThat(entity.getLastModificationDate()).isEqualTo(entity.getCreationDate());
    }

    @Test
    public void canSetLastModificationDate() {
        final FakeEntity entity = createEntity(1);
        time.waitDays(1);

        entity.setLastModificationDate(time.getNow());

        assertThat(entity.getLastModificationDate()).isEqualTo(time.getNow());
        assertThat(entity.getCreationDate()).isNotEqualTo(entity.getLastModificationDate());
    }

    private OtherFakeEntity createOtherFakeEntity(final int id) {
        final OtherFakeEntity otherFakeEntity = new OtherFakeEntity();
        otherFakeEntity.id = id;
        return otherFakeEntity;
    }

    private FakeEntity createEntity(final int id) {
        final FakeEntity fakeEntity = new FakeEntity();
        fakeEntity.id = id;
        return fakeEntity;
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
