package com.steambeat.domain.session;

import com.steambeat.domain.BaseEntity;
import org.joda.time.DateTime;

import java.util.UUID;

public class Session extends BaseEntity {

    //mongolink constructor do not delete
    public Session() {
    }

    public Session(final DateTime expirationDate) {
        this.expirationDate = expirationDate;
        this.token = UUID.randomUUID();
    }

    @Override
    public Object getId() {
        return token;
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

    public DateTime getExpirationDate() {
        return expirationDate;
    }

    public boolean isExpired() {
        return expirationDate.isBefore(new DateTime());
    }

    private UUID token;
    private String email;
    private DateTime expirationDate;
}
