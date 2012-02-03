package com.steambeat.test;

import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.test.fakeResources.*;
import com.steambeat.test.fakeResources.scraper.*;
import com.steambeat.test.fakeResources.scraper.logo.*;
import org.junit.rules.ExternalResource;
import org.restlet.*;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public class FakeInternet extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        if (component == null) {
            component = new Component();
            component.getServers().add(Protocol.HTTP, 6162);
            component.getDefaultHost().attach(createApplication());
        }
        component.start();
    }

    @Override
    protected void after() {
        try {
            component.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Restlet createApplication() {
        final Application application = new Application() {

            @Override
            public Restlet createInboundRoot() {
                final Router router = new Router();
                router.attach("/http://www.stackoverflow.com", FakeStackOverflowResource.class);
                router.attach("/http://www.badurlunknowhost.com", FakeBadurlResource.class);
                router.attach("/http://www.steambeat.com/404", Fake404urlResource.class);
                router.attach("/http://www.gameblog.fr", FakeGameblogResource.class);
                router.attach("/http://liberation.fr", FakeRedirectionResource.class);
                router.attach("/http://www.liberation.fr", FakeStatusOkResource.class);
                router.attach("/http://www.lemonde.fr", FakeStatusOkResource.class);
                router.attach("/http://lemonde.fr", FakeStatusOkResource.class);
                router.attach("/hiram/sitemap_{index}.xml.gz", FakeHiramResource.class);
                router.attach("/http://webscraper/fakesimpleuriwithouttitle", FakeSimpleUriWithoutTitle.class);
                router.attach("/http://webscraper/fakesimpleuriwithtitle", FakeSimpleUriWithTitle.class);
                router.attach("/http://webscraper/fakesimpleuriwithtitleandbadhtml", FakeSimpleUriWithTitleAndBadHtml.class);
                router.attach("/http://webscraper/fakesimpleuriwithh1section", FakeSimpleUriWithH1Section.class);
                router.attach("/http://webscraper/fakesimpleuriwithh2section", FakeSimpleUriWithH2Section.class);
                router.attach("/http://webscraper/fakesimpleuriwithh3section", FakeSimpleUriWithH3Section.class);
                router.attach("/http://webscraper/fakesimpleurinonresponsive", FakeSimpleUriNonResponsive.class);
                router.attach("/http://webscraper/fakesimpleuriwithimage", FakeSimpleUriWithImage.class);
                router.attach("/http://webscraper/logo/imgClassLogo", FakeLogoImgClassLogo.class);
                router.attach("/http://webscraper/logo/imgIdLogo", FakeLogoImgIdLogo.class);
                router.attach("/http://webscraper/logo/imgAltLogo", FakeLogoImgAltLogo.class);
                router.attach("/http://webscraper/logo/imgClassLogoPattern", FakeLogoImgClassLogoPattern.class);
                router.attach("/http://webscraper/logo/imgIdLogoPattern", FakeLogoImgIdLogoPattern.class);
                router.attach("/http://webscraper/logo/imgAltLogoPattern", FakeLogoImgAltLogoPattern.class);
                router.attach("/http://webscraper/logo/imgSimpleBackground", FakeLogoImgSimpleBackground.class);
                router.attach("/http://webscraper/logo/imgNestedChildren", FakeLogoNestedChildren.class);
                router.attach("/http://webscraper/logo/imgParent", FakeLogoParent.class);
                return router;
            }
        };
        return application;
    }

    public Uri uri(final String address) {
        return new Uri("http://localhost:6162/" + address);
    }

    private static Component component;
}
