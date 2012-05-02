package com.steambeat.domain.relation;

import com.steambeat.domain.association.Association;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.SystemTime;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.hamcrest.Matchers;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRelation {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void setUp() {
        left = TestFactories.subjects().newWebPageFor(new Association(new Uri("lemonde.fr"), UUID.randomUUID()));
        right = TestFactories.subjects().newWebPageFor(new Association(new Uri("gameblog.fr"), UUID.randomUUID()));
        relation = new RelationFactory().newRelation(left, right);
    }

    @Test
    public void canGetLeftId() {
        assertThat(relation.getFromId(), is(left.getId()));
    }

    @Test
    public void canGetLeft() {
        assertThat(relation.getLeft(), Matchers.is((Subject) left));
    }

    @Test
    public void canGetRightId() {
        assertThat(relation.getToId(), is(right.getId()));
    }

    @Test
    public void canGetRight() {
        assertThat(relation.getRight(), is((Subject) right));
    }

    @Test
    public void hasACreationDate() {
        assertThat(relation.getCreationDate(), is(time.getNow()));
    }

    private Relation relation;
    private WebPage right;
    private WebPage left;
}
