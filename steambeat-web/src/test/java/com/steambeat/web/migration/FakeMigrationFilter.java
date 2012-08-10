package com.steambeat.web.migration;

import com.steambeat.web.migration.web.MigrationFilter;
import org.restlet.*;

public class FakeMigrationFilter extends MigrationFilter {

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        return CONTINUE;
    }
}
