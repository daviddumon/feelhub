package com.steambeat.domain.reference;

import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsReferenceManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        referenceManager = new ReferenceManager();
    }

    @Test
    public void setOnlyTheOldestReferenceAsActive() {
        final Keyword goodKeyword = TestFactories.keywords().newKeyword("fr", SteambeatLanguage.forString("fr"));
        time.waitDays(1);
        final Keyword badKeyword = TestFactories.keywords().newKeyword("en", SteambeatLanguage.forString("en"));
        final ReferencePatch referencePatch = new ReferencePatch(goodKeyword.getReferenceId());
        referencePatch.addOldReferenceId(badKeyword.getReferenceId());
        final UUID oldId = badKeyword.getReferenceId();
        final UUID goodId = goodKeyword.getReferenceId();

        referenceManager.merge(referencePatch);

        final Reference badReference = Repositories.references().get(oldId);
        assertThat(badReference, notNullValue());
        assertThat(badReference.isActive(), is(false));
        final Reference goodReference = Repositories.references().get(goodId);
        assertThat(goodReference, notNullValue());
        assertThat(goodReference.isActive(), is(true));
    }

    @Test
    public void oldReferenceKeepALinkToTheNewReference() {
        final Keyword goodKeyword = TestFactories.keywords().newKeyword("fr", SteambeatLanguage.forString("fr"));
        time.waitDays(1);
        final Keyword badKeyword = TestFactories.keywords().newKeyword("en", SteambeatLanguage.forString("en"));
        final UUID oldId = badKeyword.getReferenceId();
        final UUID goodId = goodKeyword.getReferenceId();
        final ReferencePatch referencePatch = new ReferencePatch(goodId);
        referencePatch.addOldReferenceId(oldId);

        referenceManager.merge(referencePatch);

        final Reference badReference = Repositories.references().get(oldId);
        assertThat(badReference.isActive(), is(false));
        assertThat(badReference.getCurrentReferenceId(), is(goodId));
    }

    private ReferenceManager referenceManager;
}
