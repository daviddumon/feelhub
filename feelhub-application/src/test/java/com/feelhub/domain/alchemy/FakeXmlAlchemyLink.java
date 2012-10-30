package com.feelhub.domain.alchemy;

import java.io.*;

public class FakeXmlAlchemyLink extends AlchemyLink {

    @Override
    public InputStream get(final String uri) {
        File file = new File("feelhub-application/src/test/java/com/feelhub/domain/relation/alchemy/alchemy.xml");
        if (!file.exists()) {
            file = new File("src/test/java/com/feelhub/domain/relation/alchemy/alchemy.xml");
        }
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
