package com.bytedojo.domain;

import com.bytedojo.test.SystemTime;
import com.bytedojo.tools.*;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.quartz.*;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import java.io.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsHiramScheduler {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @After
    public void tearDown() throws SchedulerException {
        try {
            FileUtils.deleteDirectory(directory);
            FileUtils.deleteQuietly(new File("hiram.log"));
            if (hiramScheduler.getScheduler().isInStandbyMode() || hiramScheduler.getScheduler().isStarted()) {
                hiramScheduler.getScheduler().shutdown();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canRunApplication() throws InterruptedException, SchedulerException, ServletException {
        HiramProperties hiramProperties = new HiramProperties();
        JobKey hiramJob = new JobKey("hiramJob");
        TriggerKey triggerKey = new TriggerKey("hiramTrigger");

        hiramScheduler = new HiramScheduler();
        Thread.sleep(1500);

        assertTrue(hiramScheduler.getScheduler().isStarted());
        assertTrue(hiramScheduler.getScheduler().checkExists(hiramJob));
        assertTrue(hiramScheduler.getScheduler().checkExists(triggerKey));
        assertThat(hiramScheduler.getScheduler().getTrigger(triggerKey).getFinalFireTime(), nullValue());
        assertThat(hiramScheduler.getScheduler().getTrigger(triggerKey).getPreviousFireTime(), is(time.getNow().toDate()));
        assertThat(hiramScheduler.getScheduler().getTrigger(triggerKey).getNextFireTime(), is(time.getNow().plusSeconds(hiramProperties.getDelay()).toDate()));
    }

    @Test
    public void addRootOnlyFirstTime() throws InterruptedException, ServletException {
        XmlTransformer xmlTransformer = new XmlTransformer();

        hiramScheduler = new HiramScheduler();
        Thread.sleep(1500);

        File file = new File(directory, "sitemap_00001.xml.gz");
        Document document = xmlTransformer.readFromFile(file);
        assertThat(document.getElementsByTagName("url").getLength(), is(1));
        assertThat(document.getElementsByTagName("loc").item(0).getTextContent(), is("http://www.steambeat.com"));
        assertThat(document.getElementsByTagName("changefreq").item(0).getTextContent(), is(Frequence.always.toString()));
        assertThat(document.getElementsByTagName("priority").item(0).getTextContent(), is(String.valueOf(0.9)));
    }

    private HiramScheduler hiramScheduler;
    private String directoryName = "/hiram/sitemaps";
    private File directory = new File(directoryName);
}
