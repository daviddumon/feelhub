package com.feelhub.test.fakeResources.bing;

import org.restlet.data.MediaType;
import org.restlet.representation.FileRepresentation;
import org.restlet.resource.*;

import java.io.File;

public class BingFakeResource extends ServerResource {

    @Get
    public FileRepresentation represent() {
        String query = getQuery().getFirstValue("query").trim();
        final String filename = query.replaceAll("\\'", "");
        File file = new File("feelhub-test-tools/src/main/java/com/feelhub/test/fakeResources/bing/" + filename + ".json");
        if (!file.exists()) {
            file = new File("src/main/java/com/feelhub/test/fakeResources/bing/" + filename + ".json");
        }
        return new FileRepresentation(file, MediaType.APPLICATION_JSON);
    }
}
