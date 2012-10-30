package com.feelhub.domain.opinion;

import com.feelhub.domain.Repository;

import java.util.*;

public interface OpinionRepository extends Repository<Opinion> {

    List<Opinion> forReferenceId(final UUID referenceId);
}
