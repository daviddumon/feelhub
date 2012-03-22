package com.steambeat.domain.analytics;

import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
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

    @Before
    public void setUp() {
        left = TestFactories.subjects().newWebPageFor(new Association(new Uri("lemonde.fr"), UUID.randomUUID()));
        right = TestFactories.subjects().newWebPageFor(new Association(new Uri("gameblog.fr"), UUID.randomUUID()));
        relation = new RelationFactory().newRelation(left, right);
    }

    @Test
    public void canGetLeftId() {
        assertThat(relation.getLeftId(), is(left.getId()));
    }

    @Test
    public void canGetLeft() {
        assertThat(relation.getLeft(), Matchers.is((Subject) left));
    }

    @Test
    public void canGetRightId() {
        assertThat(relation.getRightId(), is(right.getId()));
    }

    @Test
    public void canGetRight() {
        assertThat(relation.getRight(), is((Subject) right));
    }

    private Relation relation;
    private WebPage right;
    private WebPage left;
}
