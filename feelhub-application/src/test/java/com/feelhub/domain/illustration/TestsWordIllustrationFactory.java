package com.feelhub.domain.illustration;

import com.feelhub.domain.bingsearch.*;
import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.repositories.*;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsWordIllustrationFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(BingLink.class).to(FakeBingLink.class);
                bind(SessionProvider.class).to(FakeSessionProvider.class);
            }
        });
        injector.getInstance(WordIllustrationFactory.class);
    }

    @Test
    public void canCreateIllustration() {
        final Word word = TestFactories.keywords().newWord();
        final WordIllustrationRequestEvent wordIllustrationRequestEvent = new WordIllustrationRequestEvent(word);

        DomainEventBus.INSTANCE.post(wordIllustrationRequestEvent);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size()).isEqualTo(1);
        assertThat(illustrations.get(0).getTopicId()).isEqualTo(wordIllustrationRequestEvent.getTopicId());
        assertThat(illustrations.get(0).getLink()).isEqualTo(word.getValue() + "link");
    }

    @Test
    public void checkForExistingIllustration() {
        final Word word = TestFactories.keywords().newWord();
        final WordIllustrationRequestEvent wordIllustrationRequestEvent = new WordIllustrationRequestEvent(word);
        TestFactories.illustrations().newIllustration(word.getTopicId());

        DomainEventBus.INSTANCE.post(wordIllustrationRequestEvent);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size()).isEqualTo(1);
    }
}
