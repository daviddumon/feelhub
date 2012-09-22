package com.steambeat.web.resources.json;

import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.statistics.*;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
import com.steambeat.web.*;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.joda.time.DateTime;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.*;
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
        statistics.incrementJudgmentCount(new Judgment(reference, Feeling.good));
        statistics.incrementJudgmentCount(new Judgment(reference, Feeling.bad));
        statistics.incrementJudgmentCount(new Judgment(reference, Feeling.bad));
        final ClientResource resource = restlet.newClientResource("/json/statistics?" + "start=" + new DateTime().minus(1).getMillis() + "&end=" + new DateTime().plus(1).getMillis() + "&granularity=hour" + "&referenceId=" + reference.getId());
        time.waitDays(1);

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

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

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        final JSONArray stats = new JSONArray(representation.getText());
        assertThat(stats.length(), is(2));
    }

    @Test
    public void canFetchMultipleHourWithDifferentDays() throws JSONException, IOException {
        final com.steambeat.domain.reference.Reference reference = TestFactories.references().newReference();
        final Statistics stat1 = TestFactories.statistics().newStatistics(reference, Granularity.day);
        time.waitMonths(1);
        final Statistics stat2 = TestFactories.statistics().newStatistics(reference, Granularity.day);
        final ClientResource resource = restlet.newClientResource("/json/statistics?" + "start=" + stat1.getDate().minus(1).getMillis() + "&end=" + stat2.getDate().plus(1).getMillis() + "&granularity=day" + "&referenceId=" + reference.getId());
        time.waitDays(1);

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        final JSONArray stats = new JSONArray(representation.getText());
        assertThat(stats.length(), is(2));
    }

    @Test
    @Ignore
    public void canGetStatisticsForSteam() throws IOException, JSONException {
        //final Steam steam = TestFactories.references().newSteam();
        //final Statistics stat = TestFactories.statistics().newStatistics(steam, Granularity.all);
        //time.waitMonths(1);
        //final ClientResource resource = restlet.newClientResource("/json/statistics?" + "start=" + stat.getDate().minus(1).getMillis() + "&end=" + stat.getDate().plus(1).getMillis() + "&granularity=all" + "&referenceId=" + steam.getId());
        //
        //final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();
        //
        //final JSONArray stats = new JSONArray(representation.getText());
        //assertThat(stats.length(), is(1));
    }
}