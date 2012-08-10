package com.steambeat.domain.keyword;

import com.steambeat.domain.thesaurus.Language;
import com.steambeat.test.SystemTime;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertNotNull;

public class TestsKeywordFactory {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canCreateAKeyword() {
        final KeywordFactory keywordFactory = new KeywordFactory();
        final String value = "value";
        final Language language = Language.forString("english");

        Keyword keyword = keywordFactory.createKeyword(value, language);

        assertNotNull(keyword);
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(language));
        assertThat(keyword.getId(), notNullValue());
        assertThat(keyword.getTopic(), notNullValue());
        assertThat(keyword.getCreationDate(), is(time.getNow()));
        assertThat(keyword.getLastModificationDate(), is(time.getNow()));
    }
}
