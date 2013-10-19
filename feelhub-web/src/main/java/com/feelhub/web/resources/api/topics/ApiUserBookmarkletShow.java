package com.feelhub.web.resources.api.topics;

import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.resources.api.FeelhubApiException;
import org.apache.http.auth.AuthenticationException;
import org.restlet.data.*;
import org.restlet.resource.*;

public class ApiUserBookmarkletShow extends ServerResource {

    @Post
    public void setBookmarkletShow(final Form query) {
        try {
            checkCredentials();
            final boolean bookmarkletShow = extractBookmarkletShow(query);
            CurrentUser.get().getUser().setBookmarkletShow(bookmarkletShow);
        } catch (AuthenticationException e) {
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
        } catch (FeelhubApiException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private void checkCredentials() throws AuthenticationException {
        if (!CurrentUser.get().isAuthenticated()) {
            throw new AuthenticationException();
        }
    }

    private boolean extractBookmarkletShow(final Form form) {
        if (form.getQueryString().contains("bookmarkletShow")) {
            return Boolean.parseBoolean(form.getFirstValue("bookmarkletShow"));
        } else {
            throw new FeelhubApiException();
        }
    }
}
