package com.feelhub.domain.keyword.world;

import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.UUID;

public class World extends Keyword {

    //mongolink constructor do not delete
    public World() {
    }

    public World(final UUID topicId) {
        super("", FeelhubLanguage.none(), topicId);
    }
}
