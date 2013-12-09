package com.feelhub.web.search;

import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.authentification.WebUser;
import org.jongo.Command;
import org.jongo.Jongo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mongolink.MongoSession;
import org.mongolink.test.MongolinkRule;

import java.util.List;
import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TopicFullTextSearchTest {

    @Rule
    public MongolinkRule mongolink = MongolinkRule.withPackage("com.feelhub.repositories.mapping");

    @Before
    public void setUp() throws Exception {
        jongo = mock(Jongo.class);
        textSearch = new TopicFullTextSearch(jongo, new SessionProvider() {
            @Override
            public MongoSession get() {
                return mongolink.getCurrentSession();
            }
        });
        Command command = mock(Command.class);
        when(jongo.runCommand(anyString(), anyString(), anyString())).thenReturn(command);
        results = new TagSearchResults();
        when(command.as(TagSearchResults.class)).thenReturn(results);
        currentUserIsFrench();
    }

    @After
    public void tearDown() throws Exception {
        CurrentUser.set(null);
    }

    @Test
    public void canExecuteSearch() {
        textSearch.execute("test");

        verify(jongo).runCommand("{text:#, search:#}", "tag", "test");
    }


    @Test
    public void canReturnTopics() {
        RealTopic topic = aTopic();
        addIrrelevantTopic();
        tagsReturnsHas(topic);

        List<Topic> topics = textSearch.execute("arpinum");

        assertThat(topics).hasSize(1);
    }

    @Test
    public void canFilterGivenUserLanguage() {
        currentUserIsFrench();
        tagHasAJapenesTopic();

        List<Topic> results = textSearch.execute("plop");

        assertThat(results).hasSize(0);
    }

    private void tagHasAJapenesTopic() {
        TagSearchResult result = new TagSearchResult();
        addTagFor(aTopic(), result, FeelhubLanguage.fromCode("jp"));
        results.results.add(result);
    }

    private void currentUserIsFrench() {
        User user = new User();
        user.setLanguage(FeelhubLanguage.fromCode("fr"));
        CurrentUser.set(new WebUser(user, true));
    }

    private void tagsReturnsHas(RealTopic topic) {
        TagSearchResult result = new TagSearchResult();
        addTagFor(topic, result);
        results.results.add(result);
    }

    private void addIrrelevantTopic() {
        aTopic();
    }

    private void addTagFor(RealTopic topic, TagSearchResult result) {
        FeelhubLanguage language = FeelhubLanguage.none();
        addTagFor(topic, result, language);
    }

    private void addTagFor(RealTopic topic, TagSearchResult result, FeelhubLanguage language) {
        result.obj = new Tag("arpinum");
        result.obj.addTopic(topic, language);
    }

    private RealTopic aTopic() {
        RealTopic topic = new RealTopic(UUID.randomUUID(), RealTopicType.Anniversary);
        mongolink.getCurrentSession().save(topic);
        return topic;
    }

    private Jongo jongo;
    private TopicFullTextSearch textSearch;
    private TagSearchResults results;
}
