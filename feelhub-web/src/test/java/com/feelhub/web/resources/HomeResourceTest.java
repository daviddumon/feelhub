package com.feelhub.web.resources;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.*;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.ApiFeelingSearch;
import com.google.common.collect.Lists;
import com.google.inject.*;
import org.junit.*;
import org.restlet.data.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class HomeResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        apiFeelingSearch = mock(ApiFeelingSearch.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ApiFeelingSearch.class).toInstance(apiFeelingSearch);
            }
        });
        homeResource = injector.getInstance(HomeResource.class);
    }

    @Test
    public void homeResourceIsMapped() {
        final ClientResource resource = restlet.newClientResource("/");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void hasLocalesInData() {
        final ModelAndView modelAndView = homeResource.getHome();

        assertThat(modelAndView.getData("locales")).isNotNull();
    }

    @Test
    public void hasFeelingDatasInModel() {
        final List<FeelingData> initialDatas = Lists.newArrayList();
        initialDatas.add(new FeelingData.Builder().build());
        initialDatas.add(new FeelingData.Builder().build());
        when(apiFeelingSearch.doSearch(any(Form.class))).thenReturn(initialDatas);

        final ModelAndView modelAndView = homeResource.getHome();

        assertThat(modelAndView.getData("feelingDatas")).isNotNull();
        final List<FeelingData> result = modelAndView.getData("feelingDatas");
        assertThat(result.size()).isEqualTo(2);
    }

    private HomeResource homeResource;
    private ApiFeelingSearch apiFeelingSearch;
}
