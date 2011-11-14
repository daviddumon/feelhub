package com.steambeat.test;

import com.steambeat.domain.subject.feed.Uri;
import com.steambeat.test.fakeResources.*;
import org.restlet.*;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public class FakeInternet {

    public FakeInternet() throws Exception {
        if (component == null) {
            component = new Component();
            component.getServers().add(Protocol.HTTP, 6162);
            component.getDefaultHost().attach(createApplication());
        }
        component.start();
    }

    private Restlet createApplication() {
        Application application = new Application() {

            @Override
            public Restlet createInboundRoot() {
                Router router = new Router();
                router.attach("/http://www.stackoverflow.com", FakeStackOverflowResource.class);
                router.attach("/http://www.badurlunknowhost.com", FakeBadurlResource.class);
                router.attach("/http://www.steambeat.com/404", Fake404urlResource.class);
                router.attach("/http://www.gameblog.fr", FakeGameblogResource.class);
                router.attach("/http://liberation.fr", FakeRedirectionResource.class);
                router.attach("/http://www.liberation.fr", FakeStatusOkResource.class);
                router.attach("/http://www.lemonde.fr", FakeStatusOkResource.class);
                router.attach("/http://lemonde.fr", FakeStatusOkResource.class);
                router.attach("/hiram/sitemap_{index}.xml.gz", FakeHiramResource.class);
                return router;
            }
        };
        return application;
    }

    public Uri uri(final String address) {
        return new Uri("http://localhost:6162/" + address);
    }

    public void stop() {
        try {
            component.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Component component;
}
