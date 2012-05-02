package com.steambeat.domain.relation.alchemy;

import java.io.*;

public class FakeJsonAlchemyLink extends AlchemyLink {

    @Override
    public InputStream get(final String webPageUri) {
        File file = new File("steambeat-application/src/test/java/com/steambeat/domain/relation/alchemy/alchemy.json");
        if (!file.exists()) {
            file = new File("src/test/java/com/steambeat/domain/relation/alchemy/alchemy.json");
        }
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}