package com.steambeat.domain.user;

import com.steambeat.domain.BaseEntity;
import org.apache.commons.validator.routines.EmailValidator;

public class User extends BaseEntity {

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
    public Object getId() {
        return email;
    }

    private String email;
}
