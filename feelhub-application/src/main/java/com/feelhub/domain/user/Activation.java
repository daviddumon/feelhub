package com.feelhub.domain.user;

import com.feelhub.domain.BaseEntity;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class Activation extends BaseEntity{

    protected Activation() {
        // for mongolink
    }

    public Activation(User user) {
        this.id = UUID.randomUUID();
        this.userId = user.getId();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void confirm() {
        Repositories.users().get(userId).activate();
    }
    private UUID id;

    private String userId;
}
