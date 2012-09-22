package com.steambeat.web.search;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.illustration.Illustration;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.*;
import org.junit.*;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsIllustrationSearch extends TestWithMongoRepository {

    @Rule
    public WithDomainEvent event = new WithDomainEvent();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        illustrationSearch = new IllustrationSearch(getProvider());
    }

    @Test
    public void canGetAnIllustration() {
        final Reference reference = TestFactories.references().newReference();
        final String link = "http://www.illustration.com/1.jpg";
        final Illustration illustration = TestFactories.illustrations().newIllustration(reference, link);
        List<UUID> references = Lists.newArrayList();
        references.add(reference.getId());

        final List<Illustration> illustrations = illustrationSearch.withReferences(references).execute();

        assertThat(illustrations, notNullValue());
        assertThat(illustrations.size(), is(1));
        assertThat(illustrations.get(0), is(illustration));
    }

    @Test
    public void canGetOnlySomeIllustrations() {
        final Reference ref1 = TestFactories.references().newReference();
        final Reference ref2 = TestFactories.references().newReference();
        final Reference ref3 = TestFactories.references().newReference();
        final String link = "http://www.illustration.com/1.jpg";
        TestFactories.illustrations().newIllustration(ref1, link);
        TestFactories.illustrations().newIllustration(ref2, link);
        TestFactories.illustrations().newIllustration(ref3, link);
        List<UUID> references = Lists.newArrayList();
        references.add(ref1.getId());
        references.add(ref2.getId());

        final List<Illustration> illustrations = illustrationSearch.withReferences(references).execute();

        assertThat(illustrations.size(), is(2));
    }

    private IllustrationSearch illustrationSearch;
}
