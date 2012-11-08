package com.feelhub.domain.feeling;

import com.feelhub.domain.Repository;

import java.util.*;

public interface FeelingRepository extends Repository<Feeling> {

    List<Feeling> forReferenceId(final UUID referenceId);
}
