package com.steambeat.domain.illustration;

import com.steambeat.domain.Repository;

import java.util.*;

public interface IllustrationRepository extends Repository<Illustration> {

    public List<Illustration> forReferenceId(final UUID referenceId);
}
