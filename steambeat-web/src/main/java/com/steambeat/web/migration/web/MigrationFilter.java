package com.steambeat.web.migration.web;

import com.steambeat.web.SteambeatRouter;
import org.restlet.*;
import org.restlet.routing.Filter;

public class MigrationFilter extends Filter {

    public void setSteambeatRouter(final SteambeatRouter steambeatRouter) {
        this.steambeatRouter = steambeatRouter;
    }

    public void setMigrationRouter(final MigrationRouter migrationRouter) {
        this.migrationRouter = migrationRouter;
    }

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        final Boolean ready = (Boolean) getContext().getAttributes().get("com.steambeat.ready");
        if (ready) {
            setNext(steambeatRouter);
        } else {
            setNext(migrationRouter);
        }
        return CONTINUE;
    }

    private SteambeatRouter steambeatRouter;
    private MigrationRouter migrationRouter;
}
