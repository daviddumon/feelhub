package com.steambeat.domain.reference;

import com.steambeat.domain.Repository;

public interface ReferenceRepository extends Repository<Reference> {

    Reference getActive(Object id);
}
