package com.feelhub.web.migration;

import com.feelhub.web.migration.web.MigrationFilter;
import org.restlet.*;

public class FakeMigrationFilter extends MigrationFilter {

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        return CONTINUE;
    }
}
