package com.steambeat.test.fakeRepositories;

import com.google.common.collect.Lists;
import com.steambeat.domain.Repository;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.webpage.WebPage;

import java.util.*;

public class FakeSubjectRepository implements Repository<Subject> {

    public FakeSubjectRepository(final Repository<WebPage> webPageRepository, final Repository<Concept> conceptRepository) {
        this.webPageRepository = webPageRepository;
        this.conceptRepository = conceptRepository;
    }

    @Override
    public List<Subject> getAll() {
        final ArrayList<Subject> subjects = Lists.newArrayList();
        subjects.addAll(webPageRepository.getAll());
        subjects.addAll(conceptRepository.getAll());
        return subjects;
    }

    @Override
    public void add(final Subject x) {
    }

    @Override
    public void clear() {
        webPageRepository.clear();
        conceptRepository.clear();
    }

    @Override
    public Subject get(final Object id) {
        final WebPage webPage = webPageRepository.get(id);
        return webPage == null ? conceptRepository.get(id) : webPage;
    }

    @Override
    public boolean exists(final Object id) {
        return webPageRepository.exists(id) || conceptRepository.exists(id);
    }

    private final Repository<WebPage> webPageRepository;
    private final Repository<Concept> conceptRepository;
}
