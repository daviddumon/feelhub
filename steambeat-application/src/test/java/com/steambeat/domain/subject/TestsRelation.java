package com.steambeat.domain.subject;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRelation {

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Before
    public void setUp() {
        final WebPage webPage1 = new WebPage(new Association(new Uri("lemonde.fr"), UUID.randomUUID()));
        webPage1.update();
        Repositories.webPages().add(webPage1);
        left = webPage1;
        final WebPage webPage = new WebPage(new Association(new Uri("gameblog.fr"), UUID.randomUUID()));
        webPage.update();
        Repositories.webPages().add(webPage);
        right = webPage;
        relation = new RelationFactory().newRelation(left, right);
    }

    @Test
    public void canGetLeftId() {
        assertThat(relation.getLeftId(), is(left.getId()));
    }

    @Test
    public void canGetLeft() {
        assertThat(relation.getLeft(), is((Subject) left));
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
