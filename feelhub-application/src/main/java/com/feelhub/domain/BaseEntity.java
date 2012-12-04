package com.feelhub.domain;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

public abstract class BaseEntity implements Entity {

    protected BaseEntity() {
        this.creationDate = new DateTime();
        this.lastModificationDate = this.getCreationDate();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !getClass().isAssignableFrom(o.getClass())) {
            return false;
        }
        final Entity entity = (Entity) o;
        return Objects.equal(entity.getId(), this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DateTime getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(final DateTime lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    protected DateTime creationDate;
    protected DateTime lastModificationDate;
}
