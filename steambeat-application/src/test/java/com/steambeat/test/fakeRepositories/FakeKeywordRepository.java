package com.steambeat.test.fakeRepositories;

import com.steambeat.domain.keyword.Keyword;

public class FakeKeywordRepository extends FakeRepository<Keyword> {

    //@Override
    //    public Association forIdentifier(final Identifier identifier) {
    //        try {
    //            return Iterables.find(getAll(), new Predicate<Association>() {
    //
    //                @Override
    //                public boolean apply(@Nullable final Association input) {
    //                    if (input.getIdentifier().equals(identifier.toString())) {
    //                        return true;
    //                    } else {
    //                        return false;
    //                    }
    //                }
    //            });
    //        } catch (Exception e) {
    //            return null;
    //        }
    //    }
    //
    //    @Override
    //    public Association forIdentifierAndLanguage(final Identifier identifier, final Language language) {
    //        try {
    //            return Iterables.find(getAll(), new Predicate<Association>() {
    //
    //                @Override
    //                public boolean apply(@Nullable final Association input) {
    //                    if (input.getIdentifier().equals(identifier.toString()) && input.getLanguage().equals(language.getCode())) {
    //                        return true;
    //                    } else {
    //                        return false;
    //                    }
    //                }
    //            });
    //        } catch (Exception e) {
    //            return null;
    //        }
    //    }

}
