package com.feelhub.domain.bing;

import com.feelhub.domain.admin.*;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.FakeInternet;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class BingLinkTest {

    @Rule
    public FakeInternet internet = new FakeInternet();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

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
        final List<String> illustrations = bingLink.getIllustrations("simplevalue");

        assertThat(illustrations.size()).isEqualTo(1);
        assertThat(illustrations.get(0)).isEqualTo(internet.uri("images/simplevalueillustration.jpg"));
    }

    @Test
    public void returnUpTo2IllustrationsForAKeyword() {
        final List<String> illustrations = bingLink.getIllustrations("multiplevalues");

        assertThat(illustrations.size()).isEqualTo(2);
    }

    @Test
    public void tryWithoutTypeParameterIfNoResult() {
        final List<String> illustrations = bingLink.getIllustrations("banana");

        assertThat(illustrations.size()).isEqualTo(1);
        assertThat(illustrations.get(0)).isEqualTo(internet.uri("images/simplevalueillustration.jpg"));
    }

    @Test
    public void incrementStatistic() {
        bingLink.getIllustrations("banana");

        final ApiCallEvent event = bus.lastEvent(ApiCallEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.getApi()).isEqualTo(Api.BingSearch);
        assertThat(event.getIncrement()).isEqualTo(1);
    }

    private BingLink bingLink;
}
