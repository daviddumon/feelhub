package com.feelhub.domain.alchemy;

import com.feelhub.domain.Repository;

import java.util.*;

public interface AlchemyAnalysisRepository extends Repository<AlchemyAnalysis> {

    List<AlchemyAnalysis> forTopicId(final UUID topicId);
}
