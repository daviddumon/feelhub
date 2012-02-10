package com.steambeat.test;

import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.test.fakeResources.*;
import com.steambeat.test.fakeResources.scraper.*;
import com.steambeat.test.fakeResources.scraper.bug.*;
import com.steambeat.test.fakeResources.scraper.css.CSSScraperSimple;
import com.steambeat.test.fakeResources.scraper.image.WebScraperImageWithH1Tag;
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
    }

    public void stop() {
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
                router.attach("/http://webscraper/titletag", WebScraperWithTitleTag.class);
                router.attach("/http://webscraper/titletagbadhtml", WebScraperWithTitleTagAndBadHtml.class);
                router.attach("/http://webscraper/h1tag", WebScraperWithH1Tag.class);
                router.attach("/http://webscraper/h2tag", WebScraperWithH2Tag.class);
                router.attach("/http://webscraper/logo/withclasslogo", WebScraperLogoWithClass.class);
                router.attach("/http://webscraper/logo/withidlogo", WebScraperLogoWithId.class);
                router.attach("/http://webscraper/logo/withaltlogo", WebScraperLogoWithAlt.class);
                router.attach("/http://webscraper/logo/withclasslogopattern", WebScraperLogoWithClassPattern.class);
                router.attach("/http://webscraper/logo/withidlogopattern", WebScraperLogoWithIdPattern.class);
                router.attach("/http://webscraper/logo/withaltlogopattern", WebScraperLogoWithAltPattern.class);
                router.attach("/http://webscraper/logo/backgroundimage", WebScraperBackgroundImage.class);
                router.attach("/http://webscraper/logo/logofromnested", WebScraperLogoFromNested.class);
                router.attach("/http://webscraper/image/withH1tag", WebScraperImageWithH1Tag.class);
                router.attach("/http://webscraper/bug/slatefr", WebScraperSlatefrBug.class);
                router.attach("/http://webscraper/bug/lemondefrnested", WebScraperLemondefrBug.class);
                router.attach("/http://webscraper/bug/10sportbug", WebScraper10sportBug.class);
                router.attach("/http://cssscraper/css/simple", CSSScraperSimple.class);
                router.attach("/http://webscraper/bug/tironfr", WebScraperTironfrBug.class);
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
