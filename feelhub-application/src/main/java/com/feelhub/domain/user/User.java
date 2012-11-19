package com.feelhub.domain.user;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.google.common.collect.Lists;
import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;

import java.util.*;

public class User extends BaseEntity {

    protected User() {

    }

    public User(final String id) {
        this.id = id;
        this.active = false;
    }

    public void setEmail(String email) {
        email = email.toLowerCase().trim();
        if (EmailValidator.getInstance().isValid(email)) {
            this.email = email;
        } else {
            throw new BadEmail();
        }
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getId() {
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

    public void setPassword(final String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(final String password) {
        return BCrypt.checkpw(password, this.password);
    }

    public void setFullname(final String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public FeelhubLanguage getLanguage() {
        return FeelhubLanguage.fromCode(languageCode);
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

    public SocialToken getSocialToken(final SocialNetwork network) {
        for (final SocialToken token : socialTokens) {
            if (token.is(network)) {
                return token;
            }
        }
        return null;
    }

    public void addToken(final SocialToken socialToken) {
        socialTokens.add(socialToken);
    }

    public List<SocialToken> getSocialTokens() {
        return Collections.unmodifiableList(socialTokens);
    }

    public void setLanguage(FeelhubLanguage feelhubLanguage) {
        this.languageCode = feelhubLanguage.getCode();
    }

    private String email;
    protected String password;
    private String fullname;
    private String languageCode;
    private boolean active;
    private final List<SocialToken> socialTokens = Lists.newArrayList();

    private String id;
}
