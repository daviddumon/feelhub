package com.steambeat.test.fakeSearches;

import com.google.inject.Inject;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.repositories.*;
import com.steambeat.web.search.OpinionSearch;

import java.util.List;

public class FakeOpinionSearch extends OpinionSearch {

    @Inject
    public FakeOpinionSearch(final SessionProvider provider) {
        super(provider);
    }

    @Override
    public List<Opinion> execute() {
        return Repositories.opinions().getAll();
    }
}
