package com.steambeat.domain.analytics;

import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAssociation {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canCreateAnAssociationFromUriAndUUID() {
        final String address = "http://www.steambeat.com";
        final Uri uri = new Uri(address);
        final UUID uuid = UUID.randomUUID();

        final Association association = new Association(uri, uuid);

        assertThat(association.getId(), is(uri.toString()));
        assertThat(association.getSubjectId(), is(uuid));
    }
}
