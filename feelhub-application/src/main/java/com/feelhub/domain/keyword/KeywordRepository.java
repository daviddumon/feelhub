package com.feelhub.domain.keyword;

import com.feelhub.domain.Repository;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.*;

public interface KeywordRepository extends Repository<Keyword> {

    Keyword forValueAndLanguage(final String value, final FeelhubLanguage feelhubLanguage);

    List<Keyword> forTopicId(final UUID topicId);
}
