package com.steambeat.domain.keyword;

import com.steambeat.domain.Repository;
import com.steambeat.domain.thesaurus.SteambeatLanguage;

import java.util.*;

public interface KeywordRepository extends Repository<Keyword> {

    Keyword forValueAndLanguage(final String value, final SteambeatLanguage steambeatLanguage);

    List<Keyword> forReferenceId(final UUID referenceId);
}
