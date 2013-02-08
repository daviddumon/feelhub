package com.feelhub.web.resources.api;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.WebApplicationTester;
import com.feelhub.web.authentification.*;
import com.feelhub.web.guice.GuiceTestModule;
import com.google.inject.*;
import org.apache.http.auth.AuthenticationException;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsApiCreateFeeling {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithDomainEvent events = new WithDomainEvent();

    @Before
    public void before() {
        final User fakeActiveUser = TestFactories.users().createFakeActiveUser("mail@mail.com");
        CurrentUser.set(new WebUser(fakeActiveUser, true));
        final Injector injector = Guice.createInjector(new GuiceTestModule());
        apiCreateFeeling = injector.getInstance(ApiCreateFeeling.class);
    }

    @Test
    public void postAnFeelingRequestEvent() throws AuthenticationException, JSONException {
        events.capture(FeelingRequestEvent.class);
        final JSONObject jsonObject = getGoodJson();

        apiCreateFeeling.add(jsonObject);

        final FeelingRequestEvent feelingRequestEvent = events.lastEvent(FeelingRequestEvent.class);
        assertThat(feelingRequestEvent).isNotNull();
        assertThat(feelingRequestEvent.getText()).isEqualTo(jsonObject.getString("text"));
        assertThat(feelingRequestEvent.getLanguage()).isEqualTo(FeelhubLanguage.fromCode(jsonObject.getString("languageCode")));
        assertThat(feelingRequestEvent.getUserId()).isEqualTo(CurrentUser.get().getUser().getId());
        assertThat(feelingRequestEvent.getSentiments().size()).isEqualTo(2);
    }

    @Test
    public void throwApiErrorOnMissingLanguage() throws AuthenticationException, JSONException {
        exception.expect(FeelhubApiException.class);

        apiCreateFeeling.add(new JSONObject("{text: \"test\"}"));
    }

    @Test
    public void throwApiErrorOnMissingText() throws AuthenticationException, JSONException {
        exception.expect(FeelhubApiException.class);

        apiCreateFeeling.add(new JSONObject("{languageCode: \"fr\"}"));
    }

    private JSONObject getGoodJson() throws JSONException {
        final JSONArray data = getJsonArray();
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("languageCode", FeelhubLanguage.fromCode("fr").getCode());
        jsonObject.put("text", "my feeling");
        jsonObject.put("topics", data);
        return jsonObject;
    }

    private JSONArray getJsonArray() throws JSONException {
        final JSONArray data = new JSONArray();
        final JSONObject first = new JSONObject();
        first.put("id", UUID.randomUUID().toString());
        first.put("name", "noname");
        first.put("type", "notype");
        first.put("sentiment", SentimentValue.bad.toString());
        final JSONObject second = new JSONObject();
        second.put("id", "new");
        second.put("name", "name");
        second.put("type", "other");
        second.put("sentiment", SentimentValue.bad.toString());
        data.put(first);
        data.put(second);
        return data;
    }

    private ApiCreateFeeling apiCreateFeeling;
}
