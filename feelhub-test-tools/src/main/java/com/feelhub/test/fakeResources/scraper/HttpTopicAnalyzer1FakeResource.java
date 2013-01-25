package com.feelhub.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class HttpTopicAnalyzer1FakeResource extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><head>" +
                "<meta name='description' content='http topic description'/>" +
                "</head>" +
                "<body></body></html>";
        return new StringRepresentation(html);
    }
}
