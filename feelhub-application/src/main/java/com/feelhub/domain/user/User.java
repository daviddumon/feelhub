package com.feelhub.domain.user;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.google.common.collect.Lists;
import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;

import java.util.*;

public class User extends BaseEntity {

    public User() {
        id = UUID.randomUUID();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email.toLowerCase().trim();
        if (EmailValidator.getInstance().isValid(email)) {
            this.email = email;
        } else {
            throw new BadEmail();
        }
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(email);
        stringBuilder.append(" - ");
        stringBuilder.append(fullname);
        stringBuilder.append(" - ");
        stringBuilder.append(languageCode);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(final String password) {
        return BCrypt.checkpw(password, this.password);
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(final String fullname) {
        this.fullname = fullname;
    }

    public FeelhubLanguage getLanguage() {
        return FeelhubLanguage.fromCode(languageCode);
    }

    public void setLanguage(final FeelhubLanguage feelhubLanguage) {
        this.languageCode = feelhubLanguage.getCode();
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        active = true;
    }

    public boolean getActive() {
        return active;
    }

    public SocialAuth getSocialAuth(final SocialNetwork network) {
        for (final SocialAuth token : socialAuths) {
            if (token.is(network)) {
                return token;
            }
        }
        return null;
    }

    public void addSocialAuth(final SocialAuth socialAuth) {
        socialAuths.add(socialAuth);
    }

    public List<SocialAuth> getSocialAuths() {
        return Collections.unmodifiableList(socialAuths);
    }

    public boolean getWelcomePanelShow() {
        return welcomePanelShow;
    }

    public void setWelcomePanelShow(final boolean welcomePanelShow) {
        this.welcomePanelShow = welcomePanelShow;
    }

    public boolean getButtonShow() {
        return buttonShow;
    }

    public void setButtonShow(final boolean buttonShow) {
        this.buttonShow = buttonShow;
    }

    private final List<SocialAuth> socialAuths = Lists.newArrayList();
    protected String password;
    private String email;
    private String fullname;
    private String languageCode;
    private boolean active = false;
    private final UUID id;
    private boolean welcomePanelShow = true;
    private boolean buttonShow = true;
}
