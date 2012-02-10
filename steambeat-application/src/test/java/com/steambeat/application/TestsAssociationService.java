package com.steambeat.application;

import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.*;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAssociationService {

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        associationService = new AssociationService(new CanonicalUriFinder());
    }

    @AfterClass
    public static void afterClass() {
        internet.stop();
    }

    @Test
    public void canUseEncodedResources() throws UnsupportedEncodingException {
        final Uri uri = internet.uri(URLEncoder.encode("http://www.lemonde.fr", "UTF-8"));

        final Association association = associationService.lookUp(uri);

        assertThat(association.getCanonicalUri(), is(uri.toString()));
    }

    @Test
    public void canUseAssociationFromRepository() throws UnsupportedEncodingException {
        final String address = "http://localhost:6162/http://lemonde.fr";
        final String canonicalAddress = "http://www.liberation.fr";
        final Association association = TestFactories.associations().newAssociation(address, canonicalAddress);
        final Uri uri = internet.uri(URLEncoder.encode("http://lemonde.fr", "UTF-8"));

        final Association associationFound = associationService.lookUp(uri);

        assertThat(associationFound, is(association));
    }

    @Test
    public void canSaveToRepository() {
        final Uri uri = internet.uri("http://www.gameblog.fr");

        final Association firstAssociation = associationService.lookUp(uri);

        assertThat(Repositories.associations().getAll().size(), is(1));
        assertThat(Repositories.associations().get(uri.toString()), notNullValue());
        assertThat(Repositories.associations().get(uri.toString()), is(firstAssociation));
    }

    @Test
    public void updatesOldAssociation() {
        final Uri uri = internet.uri("http://www.lemonde.fr");
        final Association oldAssociation = TestFactories.associations().newAssociation(uri.toString(), "http://www.liberation.fr");

        time.waitDays(8);
        final Association associationFound = associationService.lookUp(uri);

        assertThat(associationFound, is(oldAssociation));
        assertThat(associationFound.isAlive(), is(true));
    }

    private AssociationService associationService;
}
