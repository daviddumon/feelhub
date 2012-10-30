package com.feelhub.domain.user;

import com.feelhub.domain.BaseEntity;
import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class User extends BaseEntity {

    public User() {
        this.active = false;
        this.secret = UUID.randomUUID().toString();
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
        return email;
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

    public void setLanguageCode(final String languageCode) {
        this.languageCode = languageCode;
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

    public String getSecret() {
        return secret;
    }

    private String email;
    protected String password;
    private String fullname;
    private String languageCode;
    private boolean active;
    private final String secret;
}
