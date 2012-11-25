package com.feelhub.web.mail.mandrill;

import com.google.common.base.Objects;

public class MergeVar {

    public MergeVar(final String name, final String content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof MergeVar)) return false;

        final MergeVar mergeVar = (MergeVar) o;

        return Objects.equal(mergeVar.name, name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public String name;
    public String content;
}
