package com.feelhub.domain.relation;

import com.feelhub.domain.reference.Reference;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
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
        from = TestFactories.references().newReference();
        to = TestFactories.references().newReference();
        relation = new RelationFactory().newRelation(from, to, 1.0);
    }

    @Test
    public void canGetFromId() {
        assertThat(relation.getFromId(), is(from.getId()));
    }

    @Test
    public void canGetFrom() {
        assertThat(relation.getFrom(), Matchers.is(from));
    }

    @Test
    public void canGetToId() {
        assertThat(relation.getToId(), is(to.getId()));
    }

    @Test
    public void canGetTo() {
        assertThat(relation.getTo(), is(to));
    }

    @Test
    public void hasACreationDate() {
        assertThat(relation.getCreationDate(), is(time.getNow()));
    }

    private Relation relation;
    private Reference to;
    private Reference from;
}
