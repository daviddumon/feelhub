package com.feelhub.application;

import com.feelhub.domain.alchemy.AlchemyRequestEvent;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.illustration.*;
import com.feelhub.domain.keyword.*;
import com.feelhub.domain.keyword.uri.*;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.translation.FakeTranslator;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestKeywordService {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        keywordService = new KeywordService(new TopicService(new TopicFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver()));
    }

    @Test
    public void canLookUpAKeyword() {
        final Keyword keyword = TestFactories.keywords().newWord();

        final Keyword foundKeyword = keywordService.lookUp(keyword.getValue(), keyword.getLanguage());

        assertThat(foundKeyword).isNotNull();
    }

    @Test
    public void throwExceptionOnKeywordNotFound() {
        exception.expect(KeywordNotFound.class);

        keywordService.lookUp("any", FeelhubLanguage.forString("any"));
    }

    @Test
    public void canCreateAKeywordWithLanguageNone() {
        final String value = "Value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        assertThat(keyword).isNotNull();
        assertThat(keyword.getValue()).isEqualTo(value);
        assertThat(keyword.getLanguage()).isEqualTo(feelhubLanguage);
        assertThat(Repositories.keywords().getAll().size()).isEqualTo(1);
    }

    @Test
    public void canCreateAKeywordWithLanguageReference() {
        final String value = "Value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.reference();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        assertThat(keyword).isNotNull();
        assertThat(keyword.getValue()).isEqualTo(value);
        assertThat(keyword.getLanguage()).isEqualTo(feelhubLanguage);
        assertThat(Repositories.keywords().getAll().size()).isEqualTo(1);
    }

    @Test
    public void createAndPersistATopicFirst() {
        final String value = "value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        keywordService.createKeyword(value, feelhubLanguage);

        final List<Topic> topics = Repositories.topics().getAll();
        final List<Keyword> keywords = Repositories.keywords().getAll();
        assertThat(keywords.size()).isEqualTo(1);
        assertThat(topics.size()).isEqualTo(1);
    }

    @Test
    public void canCreateAKeywordWithTopic() {
        final Topic topic = TestFactories.topics().newTopic();
        final String value = "value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        final Keyword keyword = keywordService.createWord(value, feelhubLanguage, topic.getId());

        assertThat(keyword).isNotNull();
        assertThat(keyword.getValue()).isEqualTo(value);
        assertThat(keyword.getLanguage()).isEqualTo(feelhubLanguage);
        assertThat(Repositories.keywords().getAll().size()).isEqualTo(1);
    }

    @Test
    public void canIdentifyUris() {
        assertThat(KeywordService.isUri("http://www.google.com")).isTrue();
        assertThat(KeywordService.isUri("HTTP://www.google.com")).isTrue();
        assertThat(KeywordService.isUri("http://www.sub.google.com")).isTrue();
        assertThat(KeywordService.isUri("http://sub-test.google.com")).isTrue();
        assertThat(KeywordService.isUri("http://www.google.com/")).isTrue();
        assertThat(KeywordService.isUri("https://www.google.com")).isTrue();
        assertThat(KeywordService.isUri("https://www.google.com/")).isTrue();
        assertThat(KeywordService.isUri("www.google.com")).isTrue();
        assertThat(KeywordService.isUri("www.google.com/")).isTrue();
        assertThat(KeywordService.isUri("google.com")).isTrue();
        assertThat(KeywordService.isUri("google.com/")).isTrue();
        assertThat(KeywordService.isUri("http://google.com")).isTrue();
        assertThat(KeywordService.isUri("http://google.com/")).isTrue();
        assertThat(KeywordService.isUri("http://google.com/bin/#")).isTrue();
        assertThat(KeywordService.isUri("http://google.com/bin/#arf?id=true")).isTrue();
        assertThat(KeywordService.isUri("yala.fr")).isTrue();
        assertThat(KeywordService.isUri("yala.fr/")).isTrue();
        assertThat(KeywordService.isUri("www.%C3%A9l%C3%A9phant.com")).isTrue();

        assertThat(KeywordService.isUri("notanuri")).isFalse();
        assertThat(KeywordService.isUri("httpnotanuri")).isFalse();
        assertThat(KeywordService.isUri("http:notanuri")).isFalse();
        assertThat(KeywordService.isUri("http:/notanuri")).isFalse();
        assertThat(KeywordService.isUri("http://notanuri")).isFalse();
        assertThat(KeywordService.isUri("http://notanuri/")).isFalse();
        assertThat(KeywordService.isUri("http://notanuri/zala#lol")).isFalse();
        assertThat(KeywordService.isUri("notanuri.comm")).isFalse();
        assertThat(KeywordService.isUri(".com")).isFalse();
        assertThat(KeywordService.isUri(".fr")).isFalse();
        assertThat(KeywordService.isUri(".c")).isFalse();
        assertThat(KeywordService.isUri(".come")).isFalse();
        assertThat(KeywordService.isUri("ftp://www.google.com")).isFalse();
        assertThat(KeywordService.isUri("www.%C3%A9l%C3%A9phant.")).isFalse();
    }

    @Test
    public void translateIfNotLanguageReference() {
        final String value = "Value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.forString("fr");

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        assertThat(keyword).isNotNull();
        assertThat(keyword.getValue()).isEqualTo(value);
        assertThat(keyword.getLanguage()).isEqualTo(feelhubLanguage);
        assertThat(Repositories.keywords().getAll().size()).isEqualTo(2);
        assertThat(Repositories.keywords().getAll().get(0).getTopicId()).isEqualTo(Repositories.keywords().getAll().get(1).getTopicId());
    }

    @Test
    public void doNotCreateSameKeywordTwice() {
        final String value = "Value";

        keywordService.createKeyword(value, FeelhubLanguage.forString("fr"));
        keywordService.createKeyword(value, FeelhubLanguage.forString("de"));

        assertThat(Repositories.keywords().getAll().size()).isEqualTo(3);
        assertThat(Repositories.topics().getAll().size()).isEqualTo(1);
    }

    @Test
    public void lookUpExistingTranslation() {
        final Keyword referenceKeyword = TestFactories.keywords().newWord("Valueenglish", FeelhubLanguage.reference());

        keywordService.createKeyword("Value", FeelhubLanguage.forString("fr"));

        assertThat(Repositories.keywords().getAll().size()).isEqualTo(2);
        assertThat(Repositories.keywords().getAll().get(0).getTopicId()).isEqualTo(referenceKeyword.getTopicId());
        assertThat(Repositories.keywords().getAll().get(1).getTopicId()).isEqualTo(referenceKeyword.getTopicId());
    }

    @Test
    public void translationNeededOnTranslationError() {
        keywordService.createKeyword("Exception", FeelhubLanguage.forString("fr"));

        assertThat(Repositories.keywords().getAll().size()).isEqualTo(1);
        assertThat(((Word)Repositories.keywords().getAll().get(0)).isTranslationNeeded()).isTrue();
        //fail();
    }

    @Test
    public void canCreateUri() {
        final String value = "http://www.test.com";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.reference();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        assertThat(keyword).isNotNull();
        assertThat(keyword.getValue()).isEqualTo(value);
        assertThat(keyword.getLanguage()).isEqualTo(FeelhubLanguage.none());
        assertThat(Repositories.keywords().getAll().size()).isEqualTo(2);
    }

    @Test
    public void onUriExceptionCreateConcept() {
        final String value = "http://www.exception.com";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        assertThat(keyword).isNotNull();
        assertThat(keyword.getValue()).isEqualTo(value);
        assertThat(keyword.getLanguage()).isEqualTo(FeelhubLanguage.none());
        assertThat(Repositories.keywords().getAll().size()).isEqualTo(1);
    }

    @Test
    public void worldIsNotAnUri() {
        assertThat(KeywordService.isUri("")).isFalse();
    }

    @Test
    public void doNotRequestIllustrationForWorld() {
        bus.capture(ConceptIllustrationRequestEvent.class);
        final String value = "";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        keywordService.createKeyword(value, feelhubLanguage);

        final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = bus.lastEvent(ConceptIllustrationRequestEvent.class);
        assertThat(conceptIllustrationRequestEvent).isNull();
    }

    @Test
    public void requestUriIllustration() {
        bus.capture(UriIllustrationRequestEvent.class);
        final String value = "http://www.test.com";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.reference();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        final UriIllustrationRequestEvent uriIllustrationRequestEvent = bus.lastEvent(UriIllustrationRequestEvent.class);
        assertThat(uriIllustrationRequestEvent).isNotNull();
        assertThat(uriIllustrationRequestEvent.getTopicId()).isEqualTo(keyword.getTopicId());
    }

    @Test
    public void requestAlchemy() {
        bus.capture(AlchemyRequestEvent.class);
        final String value = "http://www.test.com";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.reference();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        final AlchemyRequestEvent alchemyRequestEvent = bus.lastEvent(AlchemyRequestEvent.class);
        assertThat(alchemyRequestEvent).isNotNull();
        assertThat(alchemyRequestEvent.getUri().getTopicId()).isEqualTo(keyword.getTopicId());
    }

    @Test
    public void requestConceptIllustration() {
        bus.capture(ConceptIllustrationRequestEvent.class);
        final String value = "value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.none();

        final Keyword keyword = keywordService.createKeyword(value, feelhubLanguage);

        final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = bus.lastEvent(ConceptIllustrationRequestEvent.class);
        assertThat(conceptIllustrationRequestEvent.getTopicId()).isEqualTo(keyword.getTopicId());
    }

    @Test
    public void lookUpIsCaseInsensitiveForConcept() {
        final String value = "Concept";
        final Keyword keyword = TestFactories.keywords().newWord(value);

        final Keyword foundKeyword = keywordService.lookUp("ConCEPT", keyword.getLanguage());

        assertThat(foundKeyword).isEqualTo(keyword);
    }

    @Test
    public void lookUpIsCaseSensitiveForUri() {
        exception.expect(KeywordNotFound.class);
        final String value = "http://www.youtube.com/ALFED";
        final Keyword keyword = TestFactories.keywords().newWord(value);

        keywordService.lookUp("http://www.youtube.com/alfed", keyword.getLanguage());
    }

    @Test
    public void createAKeywordNormalizeTheValue() {
        final String value = "concepT";

        keywordService.createKeyword(value, FeelhubLanguage.reference());

        final Keyword foundKeyword = Repositories.keywords().getAll().get(0);
        assertThat(foundKeyword.getValue()).isEqualTo("Concept");
    }

    @Test
    public void lookUpOrCreateThrowBadValueExceptionIfConceptTooSmall() {
        exception.expect(BadValueException.class);

        keywordService.lookUpOrCreate("", FeelhubLanguage.reference().getCode());
    }

    @Test
    public void lookUpThrowBadValueExceptionIfConceptTooSmall() {
        exception.expect(BadValueException.class);

        keywordService.lookUp("", FeelhubLanguage.reference());
    }

    @Test
    public void correctlyNormalizeMultiWords() {
        final String value = "la mort";

        keywordService.createKeyword(value, FeelhubLanguage.reference());

        final Keyword foundKeyword = Repositories.keywords().getAll().get(0);
        assertThat(foundKeyword.getValue()).isEqualTo("La Mort");
    }

    private KeywordService keywordService;
}
