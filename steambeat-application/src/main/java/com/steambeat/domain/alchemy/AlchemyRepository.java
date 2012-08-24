package com.steambeat.domain.alchemy;

import com.steambeat.domain.Repository;

import java.util.*;

public interface AlchemyRepository extends Repository<Alchemy> {

    List<Alchemy> forReferenceId(final UUID referenceId);
}
