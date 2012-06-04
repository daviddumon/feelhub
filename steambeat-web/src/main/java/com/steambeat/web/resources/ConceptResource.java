package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.SubjectService;
import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.UUID;

public class ConceptResource extends ServerResource {

    @Inject
    public ConceptResource(final SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    protected void doInit() throws ResourceException {
        id = UUID.fromString(getRequestAttributes().get("id").toString());
        concept = subjectService.lookUpConcept(id);
    }

    @Get
    public Representation represent() {
        return SteambeatTemplateRepresentation.createNew("concept.ftl", getContext()).with("concept", concept);
    }

    private final SubjectService subjectService;
    private UUID id;
    private Concept concept;
}
