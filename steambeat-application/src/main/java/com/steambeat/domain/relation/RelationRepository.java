package com.steambeat.domain.relation;

import com.steambeat.domain.Repository;
import com.steambeat.domain.topic.Topic;

public interface RelationRepository extends Repository<Relation> {

    Relation lookUp(final Topic from, final Topic to);
}
