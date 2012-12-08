package com.feelhub.domain.topic;

import com.feelhub.domain.topic.unusable.WorldTopic;
import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsTopicFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        topicFactory = injector.getInstance(TopicFactory.class);
    }

    @Test
    public void canCreateTopic() {
        final RealTopic realTopic = topicFactory.createRealTopic();

        assertThat(realTopic).isNotNull();
    }

    @Test
    public void canCreateWorldTopic() {
        final WorldTopic worldTopic = topicFactory.createWorldTopic();

        assertThat(worldTopic).isNotNull();
    }

    //@Test
    //public void canSetTranslationNeededForAWord() {
    //    final Word word = tagFactory.createWord("value", FeelhubLanguage.fromCountryName("english"), TestFactories.topics().newTopic().getId());
    //
    //    word.setTranslationNeeded(true);
    //
    //    assertThat(word.isTranslationNeeded()).isTrue();
    //}

    private TopicFactory topicFactory;
}