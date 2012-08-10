package com.steambeat.domain.keyword;

import com.steambeat.domain.thesaurus.Language;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TestsKeywordFactory {

    @Test
    public void canCreateAKeyword() {
        final KeywordFactory keywordFactory = new KeywordFactory();
        final String value = "value";
        final Language language = Language.forString("english");

        Keyword keyword = keywordFactory.createKeyword(value, language);

        assertNotNull(keyword);
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(language));
    }

    @Test
    public void keywordHasATopic() {

    }
}
