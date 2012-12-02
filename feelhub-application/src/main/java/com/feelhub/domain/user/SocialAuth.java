package com.feelhub.domain.user;

import com.google.common.base.Objects;

public class SocialAuth {

    @SuppressWarnings("UnusedDeclaration")
    protected SocialAuth() {

    }

    public SocialAuth(final SocialNetwork facebook, final String id, final String token) {
        this.network = facebook;
        this.id = id;
        this.token = token;
    }

    public boolean is(final SocialNetwork network) {
        return network == this.network;
    }

    public SocialNetwork getNetwork() {
        return network;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocialAuth)) {
            return false;
        }

        final SocialAuth that = (SocialAuth) o;
        return Objects.equal(that.network, network) && Objects.equal(that.id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(network, token);
    }

    private SocialNetwork network;
    private String id;
    private String token;
}
