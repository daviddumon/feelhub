package com.steambeat.test.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.steambeat.domain.association.*;
import com.steambeat.domain.thesaurus.Language;

import javax.annotation.Nullable;

public class FakeAssociationRepository extends FakeRepository<Association> implements AssociationRepository {

    @Override
    public Association forIdentifier(final Identifier identifier) {
        try {
            return Iterables.find(getAll(), new Predicate<Association>() {

                @Override
                public boolean apply(@Nullable final Association input) {
                    if (input.getIdentifier().equals(identifier.toString())) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Association forIdentifierAndLanguage(final Identifier identifier, final Language language) {
        try {
            return Iterables.find(getAll(), new Predicate<Association>() {

                @Override
                public boolean apply(@Nullable final Association input) {
                    if (input.getIdentifier().equals(identifier.toString()) && input.getLanguage().equals(language)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        } catch (Exception e) {
            return null;
        }
    }
}
