package com.feelhub.domain.alchemy;

import com.feelhub.domain.Repository;

import java.util.*;

public interface AlchemyEntityRepository extends Repository<AlchemyEntity> {

    List<AlchemyEntity> forReferenceId(final UUID referenceId);
}
