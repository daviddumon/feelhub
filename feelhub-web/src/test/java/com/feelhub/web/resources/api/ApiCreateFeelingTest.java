package com.feelhub.web.resources.api;

import com.feelhub.application.command.*;
import com.feelhub.application.command.feeling.CreateFeelingCommand;
import com.feelhub.domain.feeling.FeelingValue;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.WebApplicationTester;
import com.feelhub.web.authentification.*;
import com.google.common.util.concurrent.Futures;
import org.apache.http.auth.AuthenticationException;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class ApiCreateFeelingTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
        final User fakeActiveUser = TestFactories.users().createFakeActiveUser("mail@mail.com");
        CurrentUser.set(new WebUser(fakeActiveUser, true));
        commandBus = mock(CommandBus.class);
        apiCreateFeeling = new ApiCreateFeeling(commandBus);
    }

    @Test
    public void postAFeelingRequestEvent() throws AuthenticationException, JSONException {
        when(commandBus.execute(any(Command.class))).thenReturn(Futures.immediateCheckedFuture(UUID.randomUUID()));
        final JSONObject jsonObject = getGoodJson();

        apiCreateFeeling.add(jsonObject);

        final ArgumentCaptor<CreateFeelingCommand> captor = ArgumentCaptor.forClass(CreateFeelingCommand.class);
        verify(commandBus, atLeastOnce()).execute(captor.capture());
        final CreateFeelingCommand createFeelingCommand = captor.getValue();
        assertThat(createFeelingCommand).isNotNull();
        assertThat(createFeelingCommand.text).isEqualTo(jsonObject.getString("text"));
        assertThat(createFeelingCommand.language).isEqualTo(FeelhubLanguage.fromCode(jsonObject.getString("languageCode")));
        assertThat(createFeelingCommand.userId).isEqualTo(CurrentUser.get().getUser().getId());
    }

    @Test
    @Ignore("A refacto completement avec le changement de form")
    public void noneSentimentsAreFiltered() throws JSONException, AuthenticationException {
        when(commandBus.execute(any(Command.class))).thenReturn(Futures.immediateCheckedFuture(UUID.randomUUID()));
        final JSONObject jsonObject = getGoodJsonWithANoneSentiment();

        apiCreateFeeling.add(jsonObject);

        final ArgumentCaptor<CreateFeelingCommand> captor = ArgumentCaptor.forClass(CreateFeelingCommand.class);
        verify(commandBus, atLeastOnce()).execute(captor.capture());
        final CreateFeelingCommand createFeelingCommand = captor.getValue();
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
        return getGoodJson(getJsonArray());
    }

    private JSONObject getGoodJsonWithANoneSentiment() throws JSONException {
        return getGoodJson(getJsonArray("none", FeelingValue.bad.toString()));
    }

    private JSONObject getGoodJson(final JSONArray topics) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("languageCode", FeelhubLanguage.fromCode("fr").getCode());
        jsonObject.put("text", "my feeling");
        jsonObject.put("topics", topics);
        return jsonObject;
    }

    private JSONArray getJsonArray() throws JSONException {
        return getJsonArray(FeelingValue.bad.toString(), FeelingValue.bad.toString());
    }

    private JSONArray getJsonArray(final String firstSentimentValue, final String secondSentimentValue) throws JSONException {
        final JSONArray data = new JSONArray();
        final JSONObject first = new JSONObject();
        first.put("id", UUID.randomUUID().toString());
        first.put("name", "noname");
        first.put("type", "notype");
        first.put("sentiment", firstSentimentValue);
        final JSONObject second = new JSONObject();
        second.put("id", "new");
        second.put("name", "name");
        second.put("type", "other");
        second.put("sentiment", secondSentimentValue);
        data.put(first);
        data.put(second);
        return data;
    }

    private ApiCreateFeeling apiCreateFeeling;
    private CommandBus commandBus;
}
