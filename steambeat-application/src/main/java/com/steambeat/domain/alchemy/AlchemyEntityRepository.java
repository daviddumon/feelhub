package com.steambeat.domain.alchemy;

import com.steambeat.domain.Repository;

import java.util.*;

public interface AlchemyEntityRepository extends Repository<AlchemyEntity> {

    List<AlchemyEntity> forReferenceId(final UUID referenceId);
}
