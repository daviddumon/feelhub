package com.feelhub.web.migration.web;

import com.feelhub.web.FeelhubRouter;
import org.restlet.*;
import org.restlet.routing.Filter;

public class MigrationFilter extends Filter {

    public void setFeelhubRouter(final FeelhubRouter feelhubRouter) {
        this.feelhubRouter = feelhubRouter;
    }

    public void setMigrationRouter(final MigrationRouter migrationRouter) {
        this.migrationRouter = migrationRouter;
    }

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        final Boolean ready = (Boolean) getContext().getAttributes().get("com.feelhub.ready");
        if (ready) {
            setNext(feelhubRouter);
        } else {
            setNext(migrationRouter);
        }
        return CONTINUE;
    }

    private FeelhubRouter feelhubRouter;
    private MigrationRouter migrationRouter;
}
