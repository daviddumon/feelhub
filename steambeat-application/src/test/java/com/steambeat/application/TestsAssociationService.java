package com.steambeat.application;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.FakeUriPathResolver;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAssociationService {

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Test
    public void canCreateANewAssociation() {
        final AssociationService associationService = new AssociationService(new FakeUriPathResolver());
        final Uri uri = new Uri("http://www.steambeat.com");

        final Association association = associationService.lookUp(uri);

        assertThat(association, notNullValue());
        assertThat(association.getId(), is(uri.toString()));
    }

    @Test
    public void canCreateAllAssociationsInRepository() {
        final UriPathResolver pathResolver = new FakeUriPathResolver().thatFind(new Uri("http://www.liberation.fr"));
        final AssociationService associationService = new AssociationService(pathResolver);
        final Uri uri = new Uri("http://liberation.fr");

        final Association association = associationService.lookUp(uri);

        assertThat(association, notNullValue());
        final List<Association> associations = Repositories.associations().getAll();
        assertThat(associations.size(), is(2));
    }

    @Test
    public void returnLastAssociation() {
        final String canonicalAddress = "http://www.liberation.fr";
        final UriPathResolver pathResolver = new FakeUriPathResolver().thatFind(new Uri(canonicalAddress));
        final AssociationService associationService = new AssociationService(pathResolver);
        final Uri uri = new Uri("http://liberation.fr");

        final Association association = associationService.lookUp(uri);

        assertThat(association.getId(), is(canonicalAddress));
    }

    @Test
    public void allAssociationsFromAnUriGetSameSubjectId() {
        final String canonicalAddress = "http://www.liberation.fr";
        final UriPathResolver pathResolver = new FakeUriPathResolver().thatFind(new Uri(canonicalAddress));
        final AssociationService associationService = new AssociationService(pathResolver);
        final Uri uri = new Uri("http://liberation.fr");

        associationService.lookUp(uri);

        final List<Association> associations = Repositories.associations().getAll();
        assertThat(associations.get(0).getSubjectId(), is(associations.get(1).getSubjectId()));
    }

    @Test
    public void canUseEncodedResources() throws UnsupportedEncodingException {
        final UriPathResolver pathResolver = new FakeUriPathResolver();
        final AssociationService associationService = new AssociationService(pathResolver);
        final Uri uri = new Uri(URLEncoder.encode("http://www.lemonde.fr", "UTF-8"));

        final Association association = associationService.lookUp(uri);

        assertThat(association.getId(), is(uri.toString()));
    }

    @Test
    public void canUseAssociationFromRepository() throws UnsupportedEncodingException {
        final Association associationForCanonicalUri = createAssociationForCanonicalUri();
        final UriPathResolver uriPathResolver = new FakeUriPathResolver().thatFind(new Uri("http://www.steambeat.com"));
        final AssociationService associationService = new AssociationService(uriPathResolver);
        final Uri uri = new Uri("http://steambeat.com");

        final Association association = associationService.lookUp(uri);
        
        assertThat(association.getSubjectId(), is(associationForCanonicalUri.getSubjectId()));
    }

    private Association createAssociationForCanonicalUri() {
        final UriPathResolver uriPathResolver = new FakeUriPathResolver();
        final AssociationService associationService = new AssociationService(uriPathResolver);
        return associationService.lookUp(new Uri("http://www.steambeat.com"));
    }
}
