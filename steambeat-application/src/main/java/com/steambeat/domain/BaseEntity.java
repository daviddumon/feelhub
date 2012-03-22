package com.steambeat.domain;

import com.google.common.base.Objects;

public abstract class BaseEntity implements Entity {

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !getClass().isAssignableFrom(o.getClass())) {
            return false;
        }
        final Entity opinion = (Entity) o;
        return Objects.equal(opinion.getId(), this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
