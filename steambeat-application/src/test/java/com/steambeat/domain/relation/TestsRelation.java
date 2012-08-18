package com.steambeat.domain.relation;

import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
import org.hamcrest.Matchers;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRelation {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void setUp() {
        left = TestFactories.references().newReference();
        right = TestFactories.references().newReference();
        relation = new RelationFactory().newRelation(left, right, 1.0);
    }

    @Test
    public void canGetLeftId() {
        assertThat(relation.getFromId(), is(left.getId()));
    }

    @Test
    public void canGetLeft() {
        assertThat(relation.getLeft(), Matchers.is(left));
    }

    @Test
    public void canGetRightId() {
        assertThat(relation.getToId(), is(right.getId()));
    }

    @Test
    public void canGetRight() {
        assertThat(relation.getRight(), is(right));
    }

    @Test
    public void hasACreationDate() {
        assertThat(relation.getCreationDate(), is(time.getNow()));
    }

    private Relation relation;
    private Reference right;
    private Reference left;
}
