//package com.steambeat.sitemap.domain;
//
//import com.steambeat.sitemap.fake.*;
//import com.steambeat.test.SystemTime;
//import org.apache.commons.io.FileUtils;
//import org.junit.*;
//import org.quartz.*;
//import org.w3c.dom.Document;
//
//import javax.servlet.ServletException;
//import java.io.*;
//
//import static org.hamcrest.Matchers.*;
//import static org.junit.Assert.*;
//
//public class TestsSitemapScheduler {
//
//    @Rule
//    public SystemTime time = SystemTime.fixed();
//
//    @After
//    public void tearDown() throws SchedulerException {
//        try {
//            FileUtils.deleteDirectory(directory);
//            FileUtils.deleteQuietly(new File("hiram.log"));
//            if (sitemapScheduler.getScheduler().isInStandbyMode() || sitemapScheduler.getScheduler().isStarted()) {
//                sitemapScheduler.getScheduler().shutdown();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void canRunApplication() throws InterruptedException, SchedulerException, ServletException {
//        SitemapProperties sitemapProperties = new SitemapProperties();
//        JobKey hiramJob = new JobKey("hiramJob");
//        TriggerKey triggerKey = new TriggerKey("hiramTrigger");
//
//        sitemapScheduler = new SitemapScheduler();
//        Thread.sleep(1500);
//
//        assertTrue(sitemapScheduler.getScheduler().isStarted());
//        assertTrue(sitemapScheduler.getScheduler().checkExists(hiramJob));
//        assertTrue(sitemapScheduler.getScheduler().checkExists(triggerKey));
//        assertThat(sitemapScheduler.getScheduler().getTrigger(triggerKey).getFinalFireTime(), nullValue());
//        assertThat(sitemapScheduler.getScheduler().getTrigger(triggerKey).getPreviousFireTime(), is(time.getNow().toDate()));
//        assertThat(sitemapScheduler.getScheduler().getTrigger(triggerKey).getNextFireTime(), is(time.getNow().plusSeconds(sitemapProperties.getDelay()).toDate()));
//    }
//
//    @Test
//    public void addRootOnlyFirstTime() throws InterruptedException, ServletException {
//        XmlTransformer xmlTransformer = new XmlTransformer();
//
//        sitemapScheduler = new SitemapScheduler();
//        Thread.sleep(1500);
//
//        File file = new File(directory, "sitemap_00001.xml.gz");
//        Document document = xmlTransformer.readFromFile(file);
//        assertThat(document.getElementsByTagName("url").getLength(), is(1));
//        assertThat(document.getElementsByTagName("loc").item(0).getTextContent(), is("http://www.steambeat.com"));
//        assertThat(document.getElementsByTagName("changefreq").item(0).getTextContent(), is(Frequency.always.toString()));
//        assertThat(document.getElementsByTagName("priority").item(0).getTextContent(), is(String.valueOf(0.9)));
//    }
//
//    private SitemapScheduler sitemapScheduler;
//    private String directoryName = "/hiram/sitemaps";
//    private File directory = new File(directoryName);
//}
