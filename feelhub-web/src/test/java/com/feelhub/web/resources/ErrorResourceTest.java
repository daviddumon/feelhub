package com.feelhub.web.resources;

import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.authentification.*;
import com.feelhub.web.dto.FeelhubMessage;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.ApiFeelingSearch;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class ErrorResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        apiFeelingSearch = mock(ApiFeelingSearch.class);
        errorResource = new ErrorResource(apiFeelingSearch);
        ContextTestFactory.initResource(errorResource);
        CurrentUser.set(new WebUser(TestFactories.users().createActiveUser("test@test.com"), true));
    }

    @Test
    public void hasErrorMessageInData() {
        final ModelAndView modelAndView = errorResource.represent();

        final List<FeelhubMessage> messages = modelAndView.getData("messages");
        assertThat(messages).isNotNull();
        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0).getFeeling()).isEqualTo(SentimentValue.bad.toString());
        assertThat(messages.get(0).getSecondTimer()).isEqualTo(3);
    }

    private ApiFeelingSearch apiFeelingSearch;
    private ErrorResource errorResource;
}
