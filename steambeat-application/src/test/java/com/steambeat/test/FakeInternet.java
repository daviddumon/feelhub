package com.steambeat.test;

import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.test.fakeResources.*;
import com.steambeat.test.fakeResources.scraper.*;
import com.steambeat.test.fakeResources.scraper.bug.*;
import com.steambeat.test.fakeResources.scraper.css.CSSScraperSimple;
import com.steambeat.test.fakeResources.scraper.image.ImageExtractorResourceWithH1Tag;
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
                attachScrapersResources(router);
                return router;
            }

            private void attachScrapersResources(final Router router) {
                router.attach("/", UriScraperLogoPriority.class);
                router.attach("/uriscraper/logopriority", UriScraperLogoPriority.class);
                router.attach("/titleextractor/titletag", TitleExtractorResourceWithTitleTag.class);
                router.attach("/titleextractor/titletagbadhtml", TitleExtractorResourceWithBadHtml.class);
                router.attach("/firstelementextractor/h2tag", FirstElementExtractorResourceWithH2Tag.class);
                router.attach("/lastelementextractor/h1tag", LastElementExtractorWithH1Tag.class);
                router.attach("/lastelementextractor/bug/lemondefrnested", LastElementExtractorResourceLemondeBug.class);
                router.attach("/logoextractor/withclasslogo", LogoExtractorResourceWithClassLogo.class);
                router.attach("/logoextractor/withidlogo", LogoExtractorResourceWithIdLogo.class);
                router.attach("/logoextractor/withaltlogo", LogoExtractorResourceWithAltLogo.class);
                router.attach("/logoextractor/withclasslogopattern", LogoExtractorResourceWithClassLogoPattern.class);
                router.attach("/logoextractor/withidlogopattern", LogoExtractorResourceWithIdLogoPattern.class);
                router.attach("/logoextractor/withaltlogopattern", LogoExtractorResourceWithAltLogoPattern.class);
                router.attach("/logoextractor/backgroundimage", LogoExtractorResourceWithBackgroundImage.class);
                router.attach("/logoextractor/logofromnested", LogoExtractorResourceFromNested.class);
                router.attach("/logoextractor/bug/tironfr", LogoExtractorResourceTironBug.class);
                router.attach("/imageextractor/withH1tag", ImageExtractorResourceWithH1Tag.class);
                router.attach("/imageextractor/bug/slatefr", ImageExtractorResourceSlatefrBug.class);
                router.attach("/imageextractor/bug/10sportbug", ImageExtractorResource10SportBug.class);
                router.attach("/http://cssscraper/css/simple", CSSScraperSimple.class);
            }
        };
        return application;
    }

    public Uri uri(final String address) {
        return new Uri("http://localhost:6162/" + address);
    }

    private static Component component;
}
