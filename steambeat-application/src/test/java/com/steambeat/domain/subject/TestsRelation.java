package com.steambeat.domain.subject;

import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestsRelation {

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Before
    public void setUp()  {
        left = TestFactories.webPages().newWebPage("lemonde.fr");
        right = TestFactories.webPages().newWebPage("gameblog.fr");
        relation = new RelationFactory().newRelation(left, right);
    }

    @Test
    public void canGetLeftId() {
        assertThat(relation.getLeftId(), is("http://lemonde.fr"));
    }
    
    @Test
    public void canGetLeft() {
        assertThat(relation.getLeft(), is((Subject) left));
    }

    @Test
    public void canGetRightId() {
        assertThat(relation.getRightId(), is("http://gameblog.fr"));
    }

    @Test
    public void canGetRight() {
        assertThat(relation.getRight(), is((Subject) right));
    }

    private Relation relation;
    private WebPage right;
    private WebPage left;
}
