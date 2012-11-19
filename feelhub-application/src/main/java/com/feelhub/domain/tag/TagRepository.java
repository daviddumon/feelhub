package com.feelhub.domain.tag;

import com.feelhub.domain.Repository;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.*;

public interface TagRepository extends Repository<Tag> {

    Tag forValueAndLanguage(final String value, final FeelhubLanguage feelhubLanguage);

    Tag forTopicIdAndLanguage(final UUID topicId, final FeelhubLanguage feelhubLanguage);

    List<Tag> forTopicId(final UUID topicId);
}
