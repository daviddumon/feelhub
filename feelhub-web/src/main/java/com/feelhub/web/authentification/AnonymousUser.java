package com.feelhub.web.authentification;

import com.feelhub.domain.thesaurus.FeelhubLanguage;

public class AnonymousUser extends WebUser {

    public AnonymousUser() {
        super(null, false);
    }

    @Override
    public String getFullname() {
        return "anonymous";
    }

    @Override
    public boolean isAnonymous() {
        return true;
    }

    @Override
    public FeelhubLanguage getLanguage() {
        return FeelhubLanguage.reference();
    }

    @Override
    public boolean welcomePanelShow() {
        return false;
    }

    @Override
    public boolean bookmarkletShow() {
        return false;
    }
}
