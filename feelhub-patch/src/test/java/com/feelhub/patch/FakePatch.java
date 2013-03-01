package com.feelhub.patch;

import com.feelhub.domain.user.User;
import com.feelhub.repositories.*;

public class FakePatch extends Patch {

    public FakePatch(final SessionProvider sessionProvider) {
        super(sessionProvider);
    }

    @Override
    protected boolean withBusinessPatch() {
        return withBusinessPatch;
    }

    @Override
    protected void doBusinessPatch() {
        Repositories.users().add(new User());
    }

    @Override
    public Version version() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean withBusinessPatch;
}
