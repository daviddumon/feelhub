package com.feelhub.domain.reference;

import com.feelhub.domain.Repository;

public interface ReferenceRepository extends Repository<Reference> {

    Reference getActive(Object id);
}
