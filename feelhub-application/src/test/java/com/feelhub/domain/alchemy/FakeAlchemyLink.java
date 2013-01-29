package com.feelhub.domain.alchemy;

import com.feelhub.domain.admin.AdminStatisticCallsCounter;
import com.feelhub.domain.admin.Api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FakeAlchemyLink extends AlchemyLink {

    public FakeAlchemyLink() {
        super(new FakeAdminStatisticCallsCounter());
    }

    protected FakeAlchemyLink(AdminStatisticCallsCounter callsCounter) {
        super(callsCounter);
    }

    @Override
    protected InputStream get(final String uri) {
        if (uri.contains("www.error.com")) {
            fileName = "error.json";
        }
        File file = new File("feelhub-application/src/test/java/com/feelhub/domain/alchemy/" + fileName);
        if (!file.exists()) {
            file = new File("src/test/java/com/feelhub/domain/alchemy/" + fileName);
        }
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String fileName = "alchemy.json";

    private static class FakeAdminStatisticCallsCounter extends AdminStatisticCallsCounter {
        @Override
        public void increment(Api alchemy) {

        }
    }
}
