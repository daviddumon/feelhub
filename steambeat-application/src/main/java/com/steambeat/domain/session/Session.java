package com.steambeat.domain.session;

import com.steambeat.domain.BaseEntity;
import org.joda.time.DateTime;

import java.util.UUID;

public class Session extends BaseEntity{

    public Session() {
        this.expirationDate = new DateTime().plusHours(1);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(final UUID token) {
        this.token = token;
    }

    @Override
    public Object getId() {
        return email;
    }

    public DateTime getExpirationDate() {
        return expirationDate;
    }

    public boolean isExpired() {
        return expirationDate.isBefore(new DateTime());
    }

    public void renew() {
        this.expirationDate = new DateTime().plusHours(1);
        this.token = UUID.randomUUID();
    }

    private String email;
    private UUID token;
    private DateTime expirationDate;
}
