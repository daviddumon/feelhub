package com.steambeat.web.search;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAssociationSearch extends TestWithMongoRepository {

    @Test
    public void canSearchAWebpage() {
        final Association association = TestFactories.associations().newAssociation(new Uri("http://www.lemonde.fr"));
        final AssociationSearch associationSearch = new AssociationSearch(getProvider());

        final List<Association> subjects = associationSearch.withId(association.getId()).execute();

        assertThat(subjects.size(), is(1));
        assertThat(subjects.get(0).getId(), is(association.getId()));
    }
}
