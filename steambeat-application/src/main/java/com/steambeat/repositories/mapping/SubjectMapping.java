package com.steambeat.repositories.mapping;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
import fr.bodysplash.mongolink.domain.mapper.*;

public class SubjectMapping extends EntityMap<Subject> {

    public SubjectMapping() {
        super(Subject.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getCreationDate());
        subclass(new SubclassMap<WebPage>(WebPage.class) {
            @Override
            protected void map() {
                property(element().getTitleTag());
                property(element().getH1Tag());
                property(element().getH2Tag());
                property(element().getH3Tag());
                property(element().getImageUrlTag());
                property(element().getLogoUrlTag());
            }
        });

    }
}
