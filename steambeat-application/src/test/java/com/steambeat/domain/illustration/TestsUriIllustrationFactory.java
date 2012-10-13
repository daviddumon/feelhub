package com.steambeat.domain.illustration;

import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.scraper.FakeScraper;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsUriIllustrationFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        new UriIllustrationFactory(new FakeScraper(), new FakeSessionProvider());
    }

    @Test
    public void canCreateIllustration() {
        final Keyword keyword = TestFactories.keywords().newKeyword();
        final UriIllustrationRequestEvent uriIllustrationRequestEvent = new UriIllustrationRequestEvent(keyword.getReferenceId());

        DomainEventBus.INSTANCE.post(uriIllustrationRequestEvent);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
        assertThat(illustrations.get(0).getReferenceId(), is(uriIllustrationRequestEvent.getReferenceId()));
        assertThat(illustrations.get(0).getLink(), is("fakeillustration"));
    }
}
