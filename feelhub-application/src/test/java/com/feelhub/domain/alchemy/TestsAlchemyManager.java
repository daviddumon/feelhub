package com.feelhub.domain.alchemy;

import com.feelhub.domain.reference.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsAlchemyManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        alchemyManager = new AlchemyManager();
    }

    @Test
    public void canMergeAlchemyEntities() {
        final Reference newReference = TestFactories.references().newReference();
        final Reference oldReference = TestFactories.references().newReference();
        final ReferencePatch referencePatch = new ReferencePatch(newReference.getId());
        referencePatch.addOldReferenceId(oldReference.getId());
        TestFactories.alchemy().newAlchemyEntityEntity(oldReference.getId());

        alchemyManager.merge(referencePatch);

        final List<AlchemyEntity> alchemyEntities = Repositories.alchemyEntities().getAll();
        assertThat(alchemyEntities.get(0).getReferenceId(), is(newReference.getId()));
    }

    @Test
    public void canMergeAlchemyAnalysis() {
        final Reference newReference = TestFactories.references().newReference();
        final Reference oldReference = TestFactories.references().newReference();
        final ReferencePatch referencePatch = new ReferencePatch(newReference.getId());
        referencePatch.addOldReferenceId(oldReference.getId());
        TestFactories.alchemy().newAlchemyAnalysis(oldReference);

        alchemyManager.merge(referencePatch);

        final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().getAll();
        assertThat(alchemyAnalysisList.get(0).getReferenceId(), is(newReference.getId()));
    }

    @Test
    public void removeDuplicateAlchemyEntities() {
        final Reference newReference = TestFactories.references().newReference();
        final Reference oldReference = TestFactories.references().newReference();
        final ReferencePatch referencePatch = new ReferencePatch(newReference.getId());
        referencePatch.addOldReferenceId(oldReference.getId());
        TestFactories.alchemy().newAlchemyEntityEntity(oldReference.getId());
        TestFactories.alchemy().newAlchemyEntityEntity(newReference.getId());

        alchemyManager.merge(referencePatch);

        final List<AlchemyEntity> alchemyEntities = Repositories.alchemyEntities().getAll();
        assertThat(alchemyEntities.size(), is(1));
    }

    @Test
    public void removeDuplicateAlchemyAnalysis() {
        final Reference newReference = TestFactories.references().newReference();
        final Reference oldReference = TestFactories.references().newReference();
        final ReferencePatch referencePatch = new ReferencePatch(newReference.getId());
        referencePatch.addOldReferenceId(oldReference.getId());
        TestFactories.alchemy().newAlchemyAnalysis(oldReference);
        TestFactories.alchemy().newAlchemyAnalysis(newReference);

        alchemyManager.merge(referencePatch);

        final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().getAll();
        assertThat(alchemyAnalysisList.size(), is(1));
    }

    private AlchemyManager alchemyManager;
}
