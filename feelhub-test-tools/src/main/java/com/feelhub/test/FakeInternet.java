package com.feelhub.test;

import com.feelhub.test.fakeResources.*;
import com.feelhub.test.fakeResources.alchemy.FakeAlchemyResource;
import com.feelhub.test.fakeResources.bing.BingFakeResource;
import com.feelhub.test.fakeResources.scraper.*;
import freemarker.template.*;
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
import java.util.Locale;

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
                File file = new File("feelhub-web/src/main/webapp");
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

    private void initializeFreemarker() throws TemplateModelException {
        final Configuration configuration = new Configuration();
        configuration.setServletContextForTemplateLoading(servletContext(), "WEB-INF/templates");
        configuration.setEncoding(Locale.ROOT, "UTF-8");
        configuration.setSharedVariable("root", servletContext().getContextPath());
        component.getContext().getAttributes().put("org.freemarker.Configuration", configuration);
    }

    private ServletContext servletContext() {
        return (ServletContext) component.getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }

    private Restlet createApplication() {
        return new Application() {

            @Override
            public Restlet createInboundRoot() {
                final Router router = new Router();
                router.attach("/http://www.stackoverflow.com", FakeStackOverflowResource.class);
                router.attach("/http://www.badurlunknowhost.com", FakeBadurlResource.class);
                router.attach("/http://www.feelhub.com/404", Fake404urlResource.class);
                router.attach("/http://www.gameblog.fr", FakeGameblogResource.class);
                router.attach("/http://liberation.fr", FakeRedirectionResource.class);
                router.attach("/http://www.liberation.fr", FakeStatusOkResource.class);
                router.attach("/http://www.lemonde.fr", FakeStatusOkResource.class);
                router.attach("/http://lemonde.fr", FakeStatusOkResource.class);
                router.attach("/sitemap_{index}.xml", FakeSitemapResource.class);
                attachScrapersResources(router);
                attachAlchemyResources(router);
                attachBingResources(router);
                return router;
            }

            private void attachAlchemyResources(final Router router) {
                router.attach("/alchemyurl/URLGetRankedNamedEntities", FakeAlchemyResource.class);
            }

            private void attachScrapersResources(final Router router) {
                router.attach("/scraper", ScraperFakeResource.class);
                router.attach("/scraper/jsouptagextractor", JsoupTagExtractorFakeResource.class);
                router.attach("/scraper/jsoupmetaextractor", JsoupMetaExtractorFakeResource.class);
                router.attach("/scraper/jsoupattributextractor", JsoupAttributExtractorFakeResource.class);
                router.attach("/scraper/jsouptitleextractor", JsoupTitleExtractorFakeResource.class);
                router.attach("/scraper/httptopicanalyzer1", HttpTopicAnalyzer1FakeResource.class);
            }

            private void attachBingResources(final Router router) {
                router.attach("/bing", BingFakeResource.class);
            }
        };
    }

    public String uri(final String address) {
        return FakeInternet.SERVER_ROOT + address;
    }

    public static String SERVER_ROOT = "http://localhost:6162/";

    private static Component component;
}
