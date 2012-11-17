package com.feelhub.application;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.illustration.WordIllustrationRequestEvent;
import com.feelhub.domain.keyword.*;
import com.feelhub.domain.keyword.word.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.translation.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsWordService {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Translator.class).to(FakeTranslator.class);
            }
        });
        wordService = injector.getInstance(WordService.class);
    }

    @Test
    public void canLookUpAWord() {
        final Word word = TestFactories.keywords().newWord();

        final Word wordFound = wordService.lookUp(word.getValue(), word.getLanguage());

        assertThat(wordFound).isNotNull();
    }

    @Test
    public void canThrowWordNotFound() {
        exception.expect(WordNotFound.class);

        wordService.lookUp("any", FeelhubLanguage.fromCountryName("any"));
    }

    @Test
    public void canCreateAWordFromValueAndLanguage() {
        final String value = "Word";
        final FeelhubLanguage language = FeelhubLanguage.reference();

        final Word word = wordService.createWord(value, language);

        assertThat(word).isNotNull();
    }

    @Test
    public void createAndPersistATopicFirst() {
        final String value = "value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.reference();

        wordService.createWord(value, feelhubLanguage);

        final List<Topic> topics = Repositories.topics().getAll();
        final List<Keyword> keywords = Repositories.keywords().getAll();
        assertThat(keywords.size()).isEqualTo(1);
        assertThat(topics.size()).isEqualTo(1);
    }

    @Test
    public void translateIfLanguageDifferentFromReference() {
        final String value = "Value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.fromCountryName("fr");

        final Word word = wordService.createWord(value, feelhubLanguage);

        assertThat(word).isNotNull();
        assertThat(word.getValue()).isEqualTo(value);
        assertThat(word.getLanguage()).isEqualTo(feelhubLanguage);
        assertThat(Repositories.keywords().getAll().size()).isEqualTo(2);
        assertThat(Repositories.keywords().getAll().get(0).getTopicId()).isEqualTo(Repositories.keywords().getAll().get(1).getTopicId());
    }

    @Test
    public void doNotTranslateIfNoLanguage() {
        final String value = "Value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        final Word word = wordService.createWord(value, feelhubLanguage);

        assertThat(word).isNotNull();
        assertThat(word.getValue()).isEqualTo(value);
        assertThat(word.getLanguage()).isEqualTo(feelhubLanguage);
        assertThat(Repositories.keywords().getAll().size()).isEqualTo(1);
    }

    @Test
    public void doNotCreateSameWordTwice() {
        final String value = "Value";

        wordService.createWord(value, FeelhubLanguage.fromCountryName("fr"));
        wordService.createWord(value, FeelhubLanguage.fromCountryName("de"));

        assertThat(Repositories.keywords().getAll().size()).isEqualTo(3);
        assertThat(Repositories.topics().getAll().size()).isEqualTo(1);
    }

    @Test
    public void lookUpExistingTranslation() {
        final Word referenceWord = TestFactories.keywords().newWord("Valueenglish", FeelhubLanguage.reference());

        wordService.createWord("Value", FeelhubLanguage.fromCountryName("fr"));

        assertThat(Repositories.keywords().getAll().size()).isEqualTo(2);
        assertThat(Repositories.keywords().getAll().get(0).getTopicId()).isEqualTo(referenceWord.getTopicId());
        assertThat(Repositories.keywords().getAll().get(1).getTopicId()).isEqualTo(referenceWord.getTopicId());
    }

    @Test
    public void translationNeededOnTranslationError() {
        wordService.createWord("Exception", FeelhubLanguage.fromCountryName("fr"));

        assertThat(Repositories.keywords().getAll().size()).isEqualTo(1);
        assertThat(((Word) Repositories.keywords().getAll().get(0)).isTranslationNeeded()).isTrue();
    }

    @Test
    public void requestWordIllustration() {
        bus.capture(WordIllustrationRequestEvent.class);
        final String value = "value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        final Word word = wordService.createWord(value, feelhubLanguage);

        final WordIllustrationRequestEvent wordIllustrationRequestEvent = bus.lastEvent(WordIllustrationRequestEvent.class);
        assertThat(wordIllustrationRequestEvent.getTopicId()).isEqualTo(word.getTopicId());
    }

    @Test
    public void canCreateWordWithTypeInformation() {
        bus.capture(WordIllustrationRequestEvent.class);
        final String value = "value";
        final String type = "type";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        final Word word = wordService.createWord(value, feelhubLanguage, type);

        final WordIllustrationRequestEvent wordIllustrationRequestEvent = bus.lastEvent(WordIllustrationRequestEvent.class);
        assertThat(wordIllustrationRequestEvent.getTopicId()).isEqualTo(word.getTopicId());
        assertThat(wordIllustrationRequestEvent.getValue()).isEqualTo(word.getValue());
        assertThat(wordIllustrationRequestEvent.getType()).isEqualTo(type);
    }

    @Test
    public void lookUpIsCaseInsensitiveForWord() {
        final String value = "Word";
        final Word word = TestFactories.keywords().newWord(value);

        final Word wordFound = wordService.lookUp("WoRD", word.getLanguage());

        assertThat(wordFound).isEqualTo(word);
    }

    @Test
    public void createAKeywordNormalizeTheValue() {
        final String value = "worD";

        wordService.createWord(value, FeelhubLanguage.reference());

        final Keyword foundKeyword = Repositories.keywords().getAll().get(0);
        assertThat(foundKeyword.getValue()).isEqualTo("Word");
    }

    @Test
    public void throwBadValueIfValueEmpty() {
        exception.expect(BadValueException.class);

        wordService.lookUp("", FeelhubLanguage.reference());
    }

    @Test
    public void correctlyNormalizeMultiWords() {
        final String value = "the life";

        wordService.createWord(value, FeelhubLanguage.reference());

        final Word wordFound = (Word) Repositories.keywords().getAll().get(0);
        assertThat(wordFound.getValue()).isEqualTo("The Life");
    }

    @Test
    public void canLookupOrCreateAWord() {
        final Word word = TestFactories.keywords().newWord();

        final Word wordFound = wordService.lookUpOrCreate(word.getValue(), word.getLanguage());

        assertThat(wordFound).isNotNull();
        assertThat(wordFound).isEqualTo(word);
    }

    @Test
    public void canLookUpOrCreateWithoutExistingWord() {
        final Word wordFound = wordService.lookUpOrCreate("test", FeelhubLanguage.reference());

        assertThat(wordFound).isNotNull();
    }

    @Test
    public void checkEmptyForLookUpOrCreate() {
        exception.expect(BadValueException.class);

        wordService.lookUpOrCreate("", FeelhubLanguage.reference());
    }

    private WordService wordService;
}
