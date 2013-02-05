package com.feelhub.patch;

import com.feelhub.domain.admin.AdminStatistic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.SessionProvider;

public class PatchTest extends Patch {

    public PatchTest(SessionProvider sessionProvider) {
        super(sessionProvider);
    }

    @Override
    protected boolean withBusinessPatch() {
        return true;
    }

    @Override
    protected void doBusinessPatch() {
        for(AdminStatistic stat : Repositories.adminStatistics().getAll()) {
            System.out.println("######PATCH TEST#######" + stat.getCount());
        }
    }

    @Override
    public Version version() {
        return new Version(1);
    }
}
