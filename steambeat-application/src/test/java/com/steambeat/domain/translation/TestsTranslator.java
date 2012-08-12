package com.steambeat.domain.translation;

import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.test.*;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class TestsTranslator {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        microsoftTranslator = mock(FakeMicrosoftTranslator.class);
        translator = new Translator(microsoftTranslator);
    }

    @Test
    public void translateIfNotEnglish() {
        final Language fr = Language.forString("fr");
        final String value = "value";
        final Keyword keyword = TestFactories.keywords().newKeyword(value, fr);
        final KeywordCreatedEvent event = new KeywordCreatedEvent(keyword);

        DomainEventBus.INSTANCE.post(event);

        verify(microsoftTranslator).translate(keyword.getValue(), keyword.getLanguage().getMicrosoftLanguage());
    }

    @Test
    public void doNotTranslateIfLanguageNone() {
        final Language none = Language.none();
        final String value = "value";
        final Keyword keyword = TestFactories.keywords().newKeyword(value, none);
        final KeywordCreatedEvent event = new KeywordCreatedEvent(keyword);

        DomainEventBus.INSTANCE.post(event);

        verifyZeroInteractions(microsoftTranslator);
    }

    @Test
    public void spreadEventOnceTranslationDone() {
        bus.capture(TranslationDoneEvent.class);
        final Keyword keyword = TestFactories.keywords().newKeyword("value", Language.forString("fr"));
        final KeywordCreatedEvent event = new KeywordCreatedEvent(keyword);
        when(microsoftTranslator.translate(keyword.getValue(), keyword.getLanguage().getMicrosoftLanguage())).thenReturn("translatedValue");

        translator.translate(event);

        final TranslationDoneEvent translationDoneEvent = bus.lastEvent(TranslationDoneEvent.class);
        assertThat(translationDoneEvent, notNullValue());
        assertThat(translationDoneEvent.getDate(), is(time.getNow()));
        assertThat(translationDoneEvent.getKeyword(), is(keyword));
        assertThat(translationDoneEvent.getResult(), is("translatedValue"));
    }

    private FakeMicrosoftTranslator microsoftTranslator;
    private Translator translator;
}
