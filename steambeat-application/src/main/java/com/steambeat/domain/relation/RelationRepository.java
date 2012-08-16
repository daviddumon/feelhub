package com.steambeat.domain.relation;

import com.steambeat.domain.Repository;
import com.steambeat.domain.reference.Reference;

public interface RelationRepository extends Repository<Relation> {

    Relation lookUp(final Reference from, final Reference to);
}
