package com.feelhub.domain.user;

import com.google.common.base.Objects;

public class SocialToken {

    protected SocialToken() {

    }

    public SocialToken(final SocialNetwork network, final String value) {
        this.network = network;
        this.value = value;
    }

    public boolean is(final SocialNetwork network) {
        return network == this.network;
    }

    public SocialNetwork getNetwork() {
        return network;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocialToken)) {
            return false;
        }

        final SocialToken that = (SocialToken) o;
        return Objects.equal(that.network, network) && Objects.equal(that.value, value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(network, value);
    }

    private SocialNetwork network;

    private String value;
}
