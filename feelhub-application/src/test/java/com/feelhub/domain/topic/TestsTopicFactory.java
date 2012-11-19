package com.feelhub.domain.topic;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
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
        final Topic topic = topicFactory.createTopic();

        assertThat(topic).isNotNull();
        assertThat(topic.getId()).isNotNull();
        assertThat(topic.getCreationDate()).isEqualTo(time.getNow());
        assertThat(topic.getLastModificationDate()).isEqualTo(time.getNow());
    }

    @Test
    public void canCreateWorld() {
        final Topic world = topicFactory.createWorld();

        assertThat(world).isNotNull();
        assertThat(world.getId()).isNotNull();
        assertThat(world.getDescription(FeelhubLanguage.reference())).isEqualTo("");
        assertThat(world.getType()).isEqualTo(TopicTypes.world);
        assertThat(world.getCreationDate()).isEqualTo(time.getNow());
        assertThat(world.getLastModificationDate()).isEqualTo(time.getNow());
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