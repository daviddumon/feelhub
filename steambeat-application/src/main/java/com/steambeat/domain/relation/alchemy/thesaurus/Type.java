package com.steambeat.domain.relation.alchemy.thesaurus;

import com.google.common.base.Objects;

public class Type {

    public static Type forString(final String description) {
        return new Type(description);
    }

    private Type(final String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Type type = (Type) o;
        return Objects.equal(type.description, description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description);
    }

    public String getDescription() {
        return description;
    }

    private String description;
}
