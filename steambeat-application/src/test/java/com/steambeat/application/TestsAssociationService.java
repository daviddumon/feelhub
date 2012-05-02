package com.steambeat.application;

import com.steambeat.domain.association.Association;
import com.steambeat.domain.association.uri.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.FakeUriPathResolver;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAssociationService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void canGetAnAssociation() {
        final Uri uri = new Uri("http://www.steambeat.com");
        final Association association = TestFactories.associations().newAssociation(uri);
        final AssociationService associationService = new AssociationService(new FakeUriPathResolver());

        final Association foundAssociation = associationService.lookUp(uri);

        assertThat(foundAssociation, notNullValue());
        assertThat(foundAssociation.getId(), is(uri.toString()));
        assertThat(foundAssociation, is(association));
    }

    @Test
    public void throwErrorIfNoAssociationForUri() {
        exception.expect(AssociationNotFound.class);
        final AssociationService associationService = new AssociationService(new FakeUriPathResolver());
        final Uri uri = new Uri("http://www.steambeat.com");

        associationService.lookUp(uri);
    }

    @Test
    public void canCreateANewAssociation() {
        final AssociationService associationService = new AssociationService(new FakeUriPathResolver());
        final Uri uri = new Uri("http://www.steambeat.com");

        final Association association = associationService.createAssociationsFor(uri);

        assertThat(association, notNullValue());
        assertThat(association.getId(), is(uri.toString()));
    }

    @Test
    public void canCreateAllAssociationsInRepository() {
        final UriPathResolver pathResolver = new FakeUriPathResolver().thatFind(new Uri("http://www.liberation.fr"));
        final AssociationService associationService = new AssociationService(pathResolver);
        final Uri uri = new Uri("http://liberation.fr");

        final Association association = associationService.createAssociationsFor(uri);

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

        final Association association = associationService.createAssociationsFor(uri);

        assertThat(association.getId(), is(canonicalAddress));
    }

    @Test
    public void allAssociationsFromAnUriGetSameSubjectId() {
        final String canonicalAddress = "http://www.liberation.fr";
        final UriPathResolver pathResolver = new FakeUriPathResolver().thatFind(new Uri(canonicalAddress));
        final AssociationService associationService = new AssociationService(pathResolver);
        final Uri uri = new Uri("http://liberation.fr");

        associationService.createAssociationsFor(uri);

        final List<Association> associations = Repositories.associations().getAll();
        assertThat(associations.get(0).getSubjectId(), is(associations.get(1).getSubjectId()));
    }

    @Test
    public void canUseEncodedResources() throws UnsupportedEncodingException {
        final UriPathResolver pathResolver = new FakeUriPathResolver();
        final AssociationService associationService = new AssociationService(pathResolver);
        final Uri uri = new Uri(URLEncoder.encode("http://www.lemonde.fr", "UTF-8"));

        final Association association = associationService.createAssociationsFor(uri);

        assertThat(association.getId(), is(uri.toString()));
    }
}
