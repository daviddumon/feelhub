package com.steambeat.repositories.mapping;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.feed.Feed;
import fr.bodysplash.mongolink.domain.mapper.*;

public class SubjectMapping extends EntityMap<Subject> {

    public SubjectMapping() {
        super(Subject.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getCreationDate());
        subclass(new SubclassMap<Feed>(Feed.class) {
            @Override
            protected void map() {
                //property(element().getTitle());
            }
        });

    }
}
