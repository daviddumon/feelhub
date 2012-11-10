package com.feelhub.web.authentification;

import com.feelhub.domain.user.User;

public interface Authenticator {
    User authenticate(AuthRequest authRequest);
}
