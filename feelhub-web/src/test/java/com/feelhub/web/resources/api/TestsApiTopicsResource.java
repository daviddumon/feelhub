package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.google.inject.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.*;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TestsApiTopicsResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        topicService = mock(TopicService.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(TopicService.class).toInstance(topicService);
            }
        });
        apiTopicsResource = injector.getInstance(ApiTopicsResource.class);
        apiTopicsResource.setResponse(new Response(new Request()));
        ContextTestFactory.initResource(apiTopicsResource);
    }

    @Test
    public void canCreateWithCorrectUser() {
        final JsonRepresentation json = getJson();

        apiTopicsResource.createTopic(json);

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.SUCCESS_CREATED);
    }

    @Test
    public void cannotCreateWithoutAuthentificatedUser() {
        CurrentUser.set(new AnonymousUser());
        final JsonRepresentation json = getJson();

        apiTopicsResource.createTopic(json);

        assertThat(apiTopicsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_UNAUTHORIZED);
    }

    private JsonRepresentation getJson() {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("description", "description");
            jsonObject.put("type", "City");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(jsonObject);
    }

    private ApiTopicsResource apiTopicsResource;
    private User user;
    private TopicService topicService;
}
