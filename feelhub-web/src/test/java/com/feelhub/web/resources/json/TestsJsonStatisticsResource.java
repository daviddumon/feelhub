package com.feelhub.web.resources.json;

import com.feelhub.domain.opinion.*;
import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.statistics.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.feelhub.web.*;
import org.joda.time.DateTime;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.*;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsJsonStatisticsResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void statisticsResourceIsMapped() {
        final Reference reference = TestFactories.references().newReference();
        final ClientResource resource = restlet.newClientResource("/json/statistics?start=0&end=0&granularity=hour&referenceId=" + reference.getId());

        final Representation representation = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation.getMediaType(), is(MediaType.APPLICATION_JSON));
    }

    @Test
    public void throwJsonExceptionIfNoGranularityParameter() {
        final ClientResource resource = restlet.newClientResource("/json/statistics?start=0&end=0&referenceId=" + UUID.randomUUID());

        resource.get();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void throwJsonExceptionIfNoStartParameter() {
        final ClientResource resource = restlet.newClientResource("/json/statistics?end=0&granularity=hour&referenceId=" + UUID.randomUUID());

        resource.get();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void throwJsonExceptionIfNoEndParameter() {
        final ClientResource resource = restlet.newClientResource("/json/statistics?start=0&granularity=hour&referenceId=" + UUID.randomUUID());

        resource.get();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void throwJsonExceptionIfNoReferenceIdParameter() {
        final ClientResource resource = restlet.newClientResource("/json/statistics?start=0&end=0&granularity=hour");

        resource.get();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void canFetchSingleHour() throws JSONException, IOException {
        final Reference reference = TestFactories.references().newReference();
        final Statistics statistics = TestFactories.statistics().newStatistics(reference, Granularity.hour);
        statistics.incrementJudgmentCount(new Judgment(reference.getId(), Feeling.good));
        statistics.incrementJudgmentCount(new Judgment(reference.getId(), Feeling.bad));
        statistics.incrementJudgmentCount(new Judgment(reference.getId(), Feeling.bad));
        final ClientResource resource = restlet.newClientResource("/json/statistics?" + "start=" + new DateTime().minus(1).getMillis() + "&end=" + new DateTime().plus(1).getMillis() + "&granularity=hour" + "&referenceId=" + reference.getId());
        time.waitDays(1);

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray stats = new JSONArray(representation.getText());
        assertThat(stats.length(), is(1));
        assertThat(stats.getJSONObject(0).get("good").toString(), is("1"));
        assertThat(stats.getJSONObject(0).get("bad").toString(), is("2"));
    }

    @Test
    public void canFetchMultipleHour() throws JSONException, IOException {
        final Reference reference = TestFactories.references().newReference();
        final Statistics stat1 = TestFactories.statistics().newStatistics(reference, Granularity.hour);
        time.waitHours(1);
        final Statistics stat2 = TestFactories.statistics().newStatistics(reference, Granularity.hour);
        final ClientResource resource = restlet.newClientResource("/json/statistics?" + "start=" + stat1.getDate().minus(1).getMillis() + "&end=" + stat2.getDate().plus(1).getMillis() + "&granularity=hour" + "&referenceId=" + reference.getId());
        time.waitDays(1);

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray stats = new JSONArray(representation.getText());
        assertThat(stats.length(), is(2));
    }

    @Test
    public void canFetchMultipleHourWithDifferentDays() throws JSONException, IOException {
        final com.feelhub.domain.reference.Reference reference = TestFactories.references().newReference();
        final Statistics stat1 = TestFactories.statistics().newStatistics(reference, Granularity.day);
        time.waitMonths(1);
        final Statistics stat2 = TestFactories.statistics().newStatistics(reference, Granularity.day);
        final ClientResource resource = restlet.newClientResource("/json/statistics?" + "start=" + stat1.getDate().minus(1).getMillis() + "&end=" + stat2.getDate().plus(1).getMillis() + "&granularity=day" + "&referenceId=" + reference.getId());
        time.waitDays(1);

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray stats = new JSONArray(representation.getText());
        assertThat(stats.length(), is(2));
    }

    @Test
    @Ignore
    public void canGetStatisticsForWorld() throws IOException, JSONException {
        //final World world = TestFactories.references().newWorld();
        //final Statistics stat = TestFactories.statistics().newStatistics(world, Granularity.all);
        //time.waitMonths(1);
        //final ClientResource resource = restlet.newClientResource("/json/statistics?" + "start=" + stat.getDate().minus(1).getMillis() + "&end=" + stat.getDate().plus(1).getMillis() + "&granularity=all" + "&referenceId=" + world.getId());
        //
        //final FeelhubTemplateRepresentation representation = (FeelhubTemplateRepresentation) resource.get();
        //
        //final JSONArray stats = new JSONArray(representation.getText());
        //assertThat(stats.length(), is(1));
    }
}
