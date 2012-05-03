package com.steambeat.repositories.mapping;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.concept.Concept;
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
        property(element().getLastModificationDate());
        property(element().getDescription());
        property(element().getShortDescription());
        property(element().getIllustration());
        property(element().getScrapedDataExpirationDate());

        subclass(new SubclassMap<WebPage>(WebPage.class) {

            @Override
            protected void map() {
                property(element().getUri());
            }
        });

        subclass(new SubclassMap<Steam>(Steam.class) {

            @Override
            protected void map() {
            }
        });

        subclass(new SubclassMap<Concept>(Concept.class) {

            @Override
            protected void map() {
                property(element().getType());
                property(element().getLanguage());
                collection(element().getSubTypes());
                property(element().getWebsite());
                property(element().getGeo());
            }
        });

    }
}
