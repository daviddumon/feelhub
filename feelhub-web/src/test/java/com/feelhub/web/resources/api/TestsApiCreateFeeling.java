package com.feelhub.web.resources.api;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.FeelingRequestEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.WebApplicationTester;
import com.feelhub.web.authentification.*;
import com.feelhub.web.guice.GuiceTestModule;
import com.google.inject.*;
import org.apache.http.auth.AuthenticationException;
import org.json.JSONException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.restlet.data.Form;

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
        final Form goodForm = getGoodForm();

        apiCreateFeeling.add(goodForm);

        final FeelingRequestEvent feelingRequestEvent = events.lastEvent(FeelingRequestEvent.class);
        assertThat(feelingRequestEvent).isNotNull();
        assertThat(feelingRequestEvent.getText()).isEqualTo("my feeling +sentiment");
        assertThat(feelingRequestEvent.getLanguage()).isEqualTo(FeelhubLanguage.fromCode("fr"));
        assertThat(feelingRequestEvent.getUserId()).isEqualTo(CurrentUser.get().getUser().getId());
        assertThat(feelingRequestEvent.getTopicId().toString()).isEqualTo(goodForm.getFirstValue("topicId"));
    }

    @Test
    public void throwApiErrorOnMissingTopic() throws AuthenticationException, JSONException {
        exception.expect(FeelhubApiException.class);
        final Form parameters = new Form();
        parameters.add("text", "my feeling +sentiment");
        parameters.add("language", FeelhubLanguage.fromCode("fr").getCode());

        apiCreateFeeling.add(parameters);
    }

    @Test
    public void throwApiErrorOnMissingLanguage() throws AuthenticationException, JSONException {
        exception.expect(FeelhubApiException.class);
        final Form parameters = new Form();
        parameters.add("topicId", UUID.randomUUID().toString());
        parameters.add("text", "my feeling +sentiment");

        apiCreateFeeling.add(parameters);
    }

    @Test
    public void throwApiErrorOnMissingText() throws AuthenticationException, JSONException {
        exception.expect(FeelhubApiException.class);
        final Form parameters = new Form();
        parameters.add("topicId", UUID.randomUUID().toString());
        parameters.add("language", FeelhubLanguage.fromCode("fr").getCode());

        apiCreateFeeling.add(parameters);
    }

    private Form getGoodForm() {
        final Form parameters = new Form();
        parameters.add("topicId", UUID.randomUUID().toString());
        parameters.add("text", "my feeling +sentiment");
        parameters.add("language", FeelhubLanguage.fromCode("fr").getCode());
        return parameters;
    }

    private ApiCreateFeeling apiCreateFeeling;
}
