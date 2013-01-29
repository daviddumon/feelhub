package com.feelhub.domain.bing;

import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.FakeInternet;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class TestsBingLink {

    @Rule
    public FakeInternet internet = new FakeInternet();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        bingLink = injector.getInstance(BingLink.class);
    }

    @Test
    public void canGetSimpleMediaUrl() {
        final List<String> illustrations = bingLink.getIllustrations("simplevalue", "");

        assertThat(illustrations.size()).isEqualTo(1);
        assertThat(illustrations.get(0)).isEqualTo(internet.uri("images/simplevalueillustration.jpg"));
    }

    @Test
    public void returnUpTo2IllustrationsForAKeyword() {
        final List<String> illustrations = bingLink.getIllustrations("multiplevalues", "");

        assertThat(illustrations.size()).isEqualTo(2);
    }

    @Test
    public void canGetLinksWithTypeParameter() {
        final List<String> illustrations = bingLink.getIllustrations("pear", "fruit");

        assertThat(illustrations.size()).isEqualTo(1);
        assertThat(illustrations.get(0)).isEqualTo(internet.uri("images/simplevalueillustration.jpg"));
    }

    @Test
    public void tryWithoutTypeParameterIfNoResult() {
        final List<String> illustrations = bingLink.getIllustrations("banana", "fruit");

        assertThat(illustrations.size()).isEqualTo(1);
        assertThat(illustrations.get(0)).isEqualTo(internet.uri("images/simplevalueillustration.jpg"));
    }

    @Test
    public void incrementStatistic() {
        bingLink.getIllustrations("banana", "fruit");

        assertThat(Repositories.adminStatistics().getAll()).hasSize(1);
    }

    private BingLink bingLink;
}
