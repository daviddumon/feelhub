package com.feelhub.domain.session;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.user.User;
import org.joda.time.DateTime;

import java.util.UUID;

public class Session extends BaseEntity {

    //mongolink constructor do not delete
    public Session() {
    }

    public Session(final DateTime expirationDate, User user) {
        this.expirationDate = expirationDate;
        this.token = UUID.randomUUID();
        this.userId = user.getId();
    }

    @Override
    public Object getId() {
        return token;
    }

    public UUID getUserId() {
        return userId;
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

    public boolean isOwnedBy(final User user) {
        return !getUserId().equals(user.getId());
    }

    private UUID token;
    private UUID userId;
    private DateTime expirationDate;
}
