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
                property(element().getRelevance());
                property(element().getText());
                property(element().getCount());
                property(element().getLanguage());
                property(element().getName());
                collection(element().getSubTypes());
                property(element().getWebsite());
                property(element().getGeo());
                property(element().getDbpedia());
                property(element().getYago());
                property(element().getOpencyc());
                property(element().getUmbel());
                property(element().getFreebase());
                property(element().getCiaFactbook());
                property(element().getCensus());
                property(element().getGeonames());
                property(element().getMusicBrainz());
                property(element().getCrunchbase());
                property(element().getSemanticCrunchbase());
            }
        });

    }
}
