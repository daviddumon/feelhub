package com.steambeat.domain.illustration;

import com.google.common.collect.Lists;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsIllustrationManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        illustrationManager = new IllustrationManager();
    }

    @Test
    public void canMigrateTwoExistingIllustrations() {
        final Reference first = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(first, "link1");
        final Reference second = TestFactories.references().newReference();
        final Illustration illustrationToChange = TestFactories.illustrations().newIllustration(second, "link2");

        illustrationManager.migrate(first.getId(), Lists.newArrayList(second.getId()));

        assertThat(illustrationToChange.getReferenceId(), is(first.getId()));
    }

    @Test
    public void canDeleteDuplicateIllustrations() {
        final Reference first = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(first, "link1");
        final Reference second = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(second, "link2");

        illustrationManager.migrate(first.getId(), Lists.newArrayList(second.getId()));

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
    }

    @Test
    public void doNotCreateIfAlreadyOneIllustrationExists() {
        final Reference first = TestFactories.references().newReference();
        final Illustration illustration = TestFactories.illustrations().newIllustration(first, "link1");
        final Reference second = TestFactories.references().newReference();

        illustrationManager.migrate(first.getId(), Lists.newArrayList(second.getId()));

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
        final Illustration foundIllustration = illustrations.get(0);
        assertThat(foundIllustration, is(illustration));
    }

    private IllustrationManager illustrationManager;
}
