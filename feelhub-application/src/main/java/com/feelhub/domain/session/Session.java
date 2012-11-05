package com.feelhub.domain.session;

import com.feelhub.domain.BaseEntity;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
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
    private String userId;
    private DateTime expirationDate;
}
