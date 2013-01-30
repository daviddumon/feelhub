package com.feelhub.domain.alchemy;

import java.io.*;

public class FakeAlchemyLink extends AlchemyLink {

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
}
