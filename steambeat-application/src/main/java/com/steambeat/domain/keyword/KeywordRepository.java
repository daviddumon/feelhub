package com.steambeat.domain.keyword;

import com.steambeat.domain.Repository;
import com.steambeat.domain.thesaurus.Language;

public interface KeywordRepository extends Repository<Keyword> {

    Keyword forValueAndLanguage(final String value, final Language language);
}
