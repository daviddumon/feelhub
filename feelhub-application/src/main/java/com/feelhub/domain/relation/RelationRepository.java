package com.feelhub.domain.relation;

import com.feelhub.domain.Repository;

import java.util.*;

public interface RelationRepository extends Repository<Relation> {

    Relation lookUp(final UUID fromId, final UUID toId);

    List<Relation> forReferenceId(final UUID referenceId);
}
