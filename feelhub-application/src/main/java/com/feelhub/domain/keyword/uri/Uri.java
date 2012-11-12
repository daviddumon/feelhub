package com.feelhub.domain.keyword.uri;

import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.UUID;

public class Uri extends Keyword {

    //mongolink constructor do not delete
    public Uri() {
    }

    public Uri(final String value, final UUID topicId) {
        super(value, FeelhubLanguage.none(), topicId);
    }
}
