package com.feelhub.web.resources;

import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.ApiFeelingSearch;
import com.google.common.collect.Lists;
import com.google.inject.*;
import org.junit.*;
import org.restlet.data.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class MyFeelingsResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final User user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        apiFeelingSearch = mock(ApiFeelingSearch.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ApiFeelingSearch.class).toInstance(apiFeelingSearch);
            }
        });
        myFeelingsResource = injector.getInstance(MyFeelingsResource.class);
        ContextTestFactory.initResource(myFeelingsResource);
    }

    @Test
    public void redirectIfNotIdentified() {
        final ClientResource resource = restlet.newClientResource("/myfeelings");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.REDIRECTION_SEE_OTHER);
    }

    @Test
    public void hasLocalesInModelData() {
        final ModelAndView modelAndView = myFeelingsResource.getMyFeelings();

        assertThat(modelAndView.getData("locales")).isNotNull();
    }

    @Test
    public void fetchInitialFeelingsForUser() {
        final ModelAndView modelAndView = myFeelingsResource.getMyFeelings();

        verify(apiFeelingSearch).doSearch(any(Form.class), any(User.class));
    }

    @Test
    public void hasFeelingDatasInModel() {
        final ModelAndView modelAndView = myFeelingsResource.getMyFeelings();

        assertThat(modelAndView.getData("feelingDatas")).isNotNull();
    }

    @Test
    public void feelingDatasIsFeelingsForTopic() {
        List<FeelingData> initialDatas = Lists.newArrayList();
        initialDatas.add(new FeelingData.Builder().build());
        initialDatas.add(new FeelingData.Builder().build());
        when(apiFeelingSearch.doSearch(any(Form.class), any(User.class))).thenReturn(initialDatas);

        final ModelAndView modelAndView = myFeelingsResource.getMyFeelings();

        List<FeelingData> result = modelAndView.getData("feelingDatas");
        assertThat(result.size()).isEqualTo(2);
    }

    private MyFeelingsResource myFeelingsResource;
    private ApiFeelingSearch apiFeelingSearch;
}
