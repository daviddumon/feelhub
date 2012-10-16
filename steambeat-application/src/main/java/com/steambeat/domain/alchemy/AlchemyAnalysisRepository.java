package com.steambeat.domain.alchemy;

import com.steambeat.domain.Repository;

import java.util.*;

public interface AlchemyAnalysisRepository extends Repository<AlchemyAnalysis> {

    List<AlchemyAnalysis> forReferenceId(final UUID referenceId);
}
