package com.feelhub.domain.keyword;

import com.feelhub.domain.keyword.uri.Uri;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.keyword.world.World;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;

import java.util.UUID;

public class KeywordTestFactory {

    public Word newWord() {
        final String value = "Value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.forString("english");
        return newWord(value, feelhubLanguage);
    }

    public Word newWord(final String value) {
        return newWord(value, FeelhubLanguage.none());
    }

    public Word newWord(final String value, final FeelhubLanguage feelhubLanguage) {
        final Word word = new Word(value, feelhubLanguage, createAndPersistTopic().getId());
        Repositories.keywords().add(word);
        return word;
    }

    public Word newWord(final String value, final FeelhubLanguage feelhubLanguage, final Topic topic) {
        final Word word = new Word(value, feelhubLanguage, topic.getId());
        Repositories.keywords().add(word);
        return word;
    }

    public World newWorld() {
        final Topic topic = TestFactories.topics().newTopic();
        final World world = new World(topic.getId());
        Repositories.keywords().add(world);
        return world;
    }

    public Uri newUri() {
        final Topic topic = TestFactories.topics().newTopic();
        final Uri uri = new Uri("http://www.myuri.com/test", topic.getId());
        Repositories.keywords().add(uri);
        return uri;
    }

    public Uri newUri(final String value) {
        final Topic topic = TestFactories.topics().newTopic();
        final Uri uri = new Uri(value, topic.getId());
        Repositories.keywords().add(uri);
        return uri;
    }

    private Topic createAndPersistTopic() {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        Repositories.topics().add(topic);
        return topic;
    }
}
