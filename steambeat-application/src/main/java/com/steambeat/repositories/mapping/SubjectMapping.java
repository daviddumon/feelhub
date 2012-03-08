package com.steambeat.repositories.mapping;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.domain.subject.webpage.WebPage;
import org.mongolink.domain.mapper.*;

public class SubjectMapping extends EntityMap<Subject> {

    public SubjectMapping() {
        super(Subject.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getCreationDate());
        property(element().getDescription());
        property(element().getShortDescription());
        property(element().getIllustration());
        property(element().getScrapedDataExpirationDate());
        property(element().getSemanticDescription());

        subclass(new SubclassMap<WebPage>(WebPage.class) {
            @Override
            protected void map() {

            }
        });

        subclass(new SubclassMap<Steam>(Steam.class) {
            @Override
            protected void map() {

            }
        });

    }
}
