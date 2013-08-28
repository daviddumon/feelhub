package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.application.command.*;
import com.feelhub.application.command.feeling.CreateFeelingCommand;
import com.feelhub.domain.feeling.FeelingValue;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
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
        topicService = mock(TopicService.class);
        apiCreateFeeling = new ApiCreateFeeling(commandBus, topicService);
        topic = TestFactories.topics().newCompleteRealTopic();
        when(topicService.lookUpCurrent(any(UUID.class))).thenReturn(topic);
    }

    @Test
    public void postAFeelingRequestEvent() throws AuthenticationException, JSONException {
        when(commandBus.execute(any(Command.class))).thenReturn(Futures.immediateCheckedFuture(UUID.randomUUID()));
        final JSONObject jsonObject = getJson(FeelingValue.good);

        apiCreateFeeling.add(jsonObject);

        final ArgumentCaptor<CreateFeelingCommand> captor = ArgumentCaptor.forClass(CreateFeelingCommand.class);
        verify(commandBus, atLeastOnce()).execute(captor.capture());
        final CreateFeelingCommand createFeelingCommand = captor.getValue();
        assertThat(createFeelingCommand).isNotNull();
        assertThat(createFeelingCommand.text).isEqualTo(jsonObject.getString("text"));
        assertThat(createFeelingCommand.language).isEqualTo(FeelhubLanguage.fromCode(jsonObject.getString("languageCode")));
        assertThat(createFeelingCommand.userId).isEqualTo(CurrentUser.get().getUser().getId());
        assertThat(createFeelingCommand.topicId).isEqualTo(topic.getCurrentId());
        assertThat(createFeelingCommand.feelingValue).isEqualTo(FeelingValue.valueOf(jsonObject.getString("feelingValue")));
    }

    @Test
    public void throwApiErrorOnMissingLanguage() throws AuthenticationException, JSONException {
        exception.expect(FeelhubApiException.class);

        apiCreateFeeling.add(new JSONObject("{text:\"my feeling\", topicId:" + topic.getCurrentId() + ",feelingValue: \"good\" }"));
    }

    @Test
    public void throwApiErrorOnMissingText() throws AuthenticationException, JSONException {
        exception.expect(FeelhubApiException.class);

        apiCreateFeeling.add(new JSONObject("{languageCode:\"en\", topicId:" + topic.getCurrentId() + ",feelingValue: \"good\" }"));
    }

    @Test
    public void throwApiErrorOnMissingTopic() throws AuthenticationException, JSONException {
        exception.expect(FeelhubApiException.class);

        apiCreateFeeling.add(new JSONObject("{languageCode:\"en\", text: \"my feeling\",feelingValue: \"good\" }"));
    }

    @Test
    public void throwApiErrorOnMissingFeelingValue() throws AuthenticationException, JSONException {
        exception.expect(FeelhubApiException.class);

        apiCreateFeeling.add(new JSONObject("{languageCode:\"en\", text: \"my feeling\",topicId:" + topic.getCurrentId() + "}"));
    }

    private JSONObject getJson(final FeelingValue feelingValue) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("languageCode", FeelhubLanguage.fromCode("fr").getCode());
        jsonObject.put("text", "my feeling");
        jsonObject.put("topicId", topic.getCurrentId());
        jsonObject.put("feelingValue", feelingValue.toString());
        return jsonObject;
    }

    private ApiCreateFeeling apiCreateFeeling;
    private CommandBus commandBus;
    private TopicService topicService;
    private RealTopic topic;
}
