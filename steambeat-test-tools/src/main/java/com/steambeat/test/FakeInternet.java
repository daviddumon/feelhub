package com.steambeat.test;

import com.steambeat.test.fakeResources.*;
import com.steambeat.test.fakeResources.alchemy.FakeAlchemyResource;
import com.steambeat.test.fakeResources.scraper.UriScraperLogoPriority;
import com.steambeat.test.fakeResources.scraper.extractors.*;
import com.steambeat.test.fakeResources.scraper.tools.*;
import org.apache.commons.io.FilenameUtils;
import org.junit.rules.ExternalResource;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.restlet.*;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import javax.servlet.ServletContext;
import java.io.File;

public class FakeInternet extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        if (component == null) {
            component = new Component();
            createContextForComponent();
            component.getServers().add(Protocol.HTTP, 6162);
            component.getDefaultHost().attach(createApplication());
            initializeFreemarker();
        }
        component.start();
    }

    private void createContextForComponent() {
        final Context context = new Context();
        context.getAttributes().put("org.restlet.ext.servlet.ServletContext", mockServletContext());
        component.setContext(context);
    }

    public ServletContext mockServletContext() {
        final ServletContext servletContext = Mockito.mock(ServletContext.class);
        Mockito.when(servletContext.getRealPath(Matchers.anyString())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(final InvocationOnMock invocation) throws Throwable {
                File file = new File("steambeat-web/src/main/webapp");
                if (!file.exists()) {
                    file = new File("src/main/webapp");
                }
                return FilenameUtils.concat(file.getAbsolutePath(),
                        ((String) invocation.getArguments()[0]).substring(1));
            }
        });
        Mockito.when(servletContext.getContextPath()).thenReturn(FakeInternet.SERVER_ROOT);
        Mockito.when(servletContext.getContextPath()).thenReturn("/");
        return servletContext;
    }

    private void initializeFreemarker() {
        //final Configuration configuration = new Configuration();
        //configuration.setServletContextForTemplateLoading(servletContext(), "WEB-INF/templates");
        //configuration.setEncoding(Locale.ROOT, "UTF-8");
        //configuration.addAutoImport("head", "/head.ftl");
        //configuration.addAutoImport("body", "/body.ftl");
        //configuration.setSharedVariable("root", servletContext().getContextPath());
        //configuration.setSharedVariable("dev", steambeatWebProperties.isDev());
        //configuration.setSharedVariable("domain", steambeatWebProperties.getDomain());
        //configuration.setSharedVariable("buildtime", steambeatWebProperties.getBuildTime());
        //getContext().getAttributes().put("org.freemarker.Configuration", configuration);
    }

    private Restlet createApplication() {
        return new Application() {

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
                router.attach("/sitemap_{index}.xml", FakeSitemapResource.class);
                attachScrapersResources(router);
                attachAlchemyResources(router);
                return router;
            }

            private void attachAlchemyResources(final Router router) {
                router.attach("/alchemyurl/URLGetRankedNamedEntities", FakeAlchemyResource.class);
            }

            private void attachScrapersResources(final Router router) {
                router.attach("/", UriScraperLogoPriority.class);
                router.attach("/uriscraper/logopriority", UriScraperLogoPriority.class);
                router.attach("/titleextractor/titletag", TitleExtractorResourceWithTitleTag.class);
                router.attach("/titleextractor/titletagbadhtml", TitleExtractorResourceWithBadHtml.class);
                router.attach("/firstelementextractor/h2tag", FirstElementExtractorResourceWithH2Tag.class);
                router.attach("/lastelementextractor/h1tag", LastElementExtractorWithH1TagResource.class);
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
                router.attach("/logoextractor/fromcss", LogoExtractorResourceFromCss.class);
                router.attach("/logoextractor/withbannerpattern", LogoExtractorResourceWithBannerPattern.class);
                router.attach("/logoextractor/withoutTLD", LogoExtractorResourceWithoutTLD.class);
                router.attach("/logoextractor/io9bug", LogoExtractorResourceIo9bug.class);
                router.attach("/imageextractor/withH1tag", ImageExtractorResourceWithH1Tag.class);
                router.attach("/imageextractor/bug/slatefr", ImageExtractorResourceSlatefrBug.class);
                router.attach("/imageextractor/bug/10sportbug", ImageExtractorResource10SportBug.class);
                router.attach("/imageextractor/bug/liberation", ImageExtractorResourceLiberationBug.class);
                router.attach("/tools/cssminer/simple", CSSMinerSimple.class);
                router.attach("/css/css1", CSSMinerCss1.class);
                router.attach("/css/css2", CSSMinerCss2.class);
            }
        };
    }

    public String uri(final String address) {
        return FakeInternet.SERVER_ROOT + address;
    }

    private static String SERVER_ROOT = "http://localhost:6162/";

    private static Component component;
}
