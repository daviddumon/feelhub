package com.steambeat.domain.relation;

import com.steambeat.domain.Repository;
import com.steambeat.domain.subject.Subject;

public interface RelationRepository extends Repository<Relation> {

    Relation lookUp(final Subject from, final Subject to);
}
