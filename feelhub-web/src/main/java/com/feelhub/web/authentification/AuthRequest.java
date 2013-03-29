package com.feelhub.web.authentification;

public class AuthRequest {

    public static AuthRequest socialNetwork(final String id) {
        final AuthRequest result = new AuthRequest();
        result.authMethod = AuthMethod.SOCIALNETWORK;
        result.userId = id;
        return result;
    }

    private AuthRequest() {

    }

    public AuthRequest(final String userId, final String password, final boolean remember) {
        this.userId = userId;
        this.password = password;
        this.remember = remember;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public AuthMethod getAuthMethod() {
        return authMethod;
    }

    public boolean isRemember() {
        return remember;
    }

    private AuthMethod authMethod = AuthMethod.FEELHUB;

    private String password;
    private String userId;
    private boolean remember;
}
