package com.steambeat.domain.relation;

import com.steambeat.domain.Repository;
import com.steambeat.domain.reference.Reference;

import java.util.*;

public interface RelationRepository extends Repository<Relation> {

    Relation lookUp(final Reference from, final Reference to);

    List<Relation> forReferenceId(final UUID referenceId);
}
