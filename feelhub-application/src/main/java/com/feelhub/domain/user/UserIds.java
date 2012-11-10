package com.feelhub.domain.user;

public final class UserIds {

    private UserIds() {

    }

    public static String facebook(String id) {
        return String.format("FB:%s", id);
    }
}
