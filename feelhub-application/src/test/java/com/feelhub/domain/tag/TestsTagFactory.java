package com.feelhub.domain.tag;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Fail.fail;

public class TestsTagFactory {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        tagFactory = injector.getInstance(TagFactory.class);
    }

    @Test
    @Ignore
    public void canCreateTag() {
        fail();
    }

    //@Test
        //public void canCreateKeywordsWithSameTopic() {
        //    final Topic topic = TestFactories.topics().newTopic();
        //
        //    final Word word1 = tagFactory.createWord("value1", FeelhubLanguage.reference(), topic.getId());
        //    final Word word2 = tagFactory.createWord("value2", FeelhubLanguage.reference(), topic.getId());
        //
        //    assertThat(word1.getTopicId()).isEqualTo(word2.getTopicId());
        //}

    private TagFactory tagFactory;
}
