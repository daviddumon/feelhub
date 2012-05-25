package com.steambeat.test.testFactories;

import com.steambeat.domain.alchemy.NamedEntity;
import com.steambeat.domain.association.Association;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.*;

import java.util.*;

public class SubjectFactoryForTest {

    public WebPage newWebPage() {
        final Association association = new Association(new Uri("http://www.fake.com/" + UUID.randomUUID().toString()), UUID.randomUUID());
        return newWebPageFor(association);
    }

    public WebPage newWebPageFor(final Association association) {
        final WebPage webPage = new WebPage(association);
        webPage.setScraper(new FakeUriScraper());
        Repositories.associations().add(association);
        Repositories.subjects().add(webPage);
        return webPage;
    }

    public Steam newSteam() {
        final Steam steam = new Steam(UUID.randomUUID());
        steam.setScraper(new FakeUriScraper());
        Repositories.subjects().add(steam);
        return steam;
    }

    public Concept newConcept() {
        final List<NamedEntity> namedEntities = TestFactories.alchemy().namedEntityWith1Keyword();
        final Concept concept = new ConceptFactory(new FakeBingLink()).newConcept(namedEntities.get(0));
        concept.setScraper(new FakeUriScraper());
        Repositories.subjects().add(concept);
        return concept;
    }
}
