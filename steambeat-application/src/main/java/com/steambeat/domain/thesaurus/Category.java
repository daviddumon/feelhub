package com.steambeat.domain.thesaurus;

import com.google.common.base.Objects;

public class Category {
    public static Category forString(final String type) {
        final Category category = new Category();
        category.text = type;
        return category;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Category category = (Category) o;
        return Objects.equal(category.text, text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }

    private String text;
}
