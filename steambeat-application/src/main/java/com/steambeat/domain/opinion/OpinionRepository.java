package com.steambeat.domain.opinion;

import com.steambeat.domain.Repository;

import java.util.*;

public interface OpinionRepository extends Repository<Opinion> {

    List<Opinion> forReferenceId(final UUID referenceId);
}
