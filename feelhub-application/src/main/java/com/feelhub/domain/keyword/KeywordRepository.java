package com.feelhub.domain.keyword;

import com.feelhub.domain.Repository;
import com.feelhub.domain.keyword.uri.Uri;
import com.feelhub.domain.keyword.world.World;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.*;

public interface KeywordRepository extends Repository<Keyword> {

    Keyword forValueAndLanguage(final String value, final FeelhubLanguage feelhubLanguage);

    Keyword forTopicIdAndLanguage(final UUID topicId, final FeelhubLanguage feelhubLanguage);

    List<Keyword> forTopicId(final UUID topicId);

    World world();

    Uri getUri(final UUID id);
}
