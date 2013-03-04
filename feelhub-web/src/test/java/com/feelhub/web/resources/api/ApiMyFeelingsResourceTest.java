package com.feelhub.web.resources.api;

import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import com.google.inject.*;
import org.json.JSONException;
import org.junit.*;
import org.restlet.data.*;

import java.io.IOException;
import java.util.List;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class ApiMyFeelingsResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
        user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        apiFeelingSearchMock = mock(ApiFeelingSearch.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ApiFeelingSearch.class).toInstance(apiFeelingSearchMock);
            }
        });
        apiMyFeelingsResource = injector.getInstance(ApiMyFeelingsResource.class);
    }

    @Test
    public void errorIfBadUser() {
        final ClientResource clientResource = restlet.newClientResource("/api/myfeelings");

        clientResource.get();

        assertThat(clientResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_UNAUTHORIZED);
    }

    @Test
    public void hasFeelings() throws IOException, JSONException {
        List<FeelingData> feelingDatas = Lists.newArrayList();
        feelingDatas.add(new FeelingData.Builder().build());
        feelingDatas.add(new FeelingData.Builder().build());
        when(apiFeelingSearchMock.doSearch(any(Form.class), any(User.class))).thenReturn(feelingDatas);

        final ModelAndView myFeelings = apiMyFeelingsResource.getMyFeelings();

        assertThat(myFeelings).isNotNull();
        final List<FeelingData> result = (List<FeelingData>) myFeelings.getData("feelingDatas");
        assertThat(result.size()).isEqualTo(2);
    }

    private ApiFeelingSearch apiFeelingSearchMock;
    private User user;
    private ApiMyFeelingsResource apiMyFeelingsResource;
}
