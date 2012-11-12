package com.feelhub.repositories.mapping;

import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.keyword.uri.Uri;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.keyword.world.World;
import org.mongolink.domain.mapper.*;

public class KeywordMapping extends EntityMap<Keyword> {

    public KeywordMapping() {
        super(Keyword.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getValue());
        property(element().getTopicId());
        property(element().getCreationDate());
        property(element().getLastModificationDate());

        subclass(new SubclassMap<Word>(Word.class) {

            @Override
            protected void map() {
                property(element().getLanguageCode());
                property(element().isTranslationNeeded());
            }
        });

        subclass(new SubclassMap<Uri>(Uri.class) {

            @Override
            protected void map() {

            }
        });

        subclass(new SubclassMap<World>(World.class) {

            @Override
            protected void map() {
            }
        });
    }
}
