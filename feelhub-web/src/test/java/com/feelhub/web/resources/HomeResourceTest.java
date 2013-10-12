package com.feelhub.web.resources;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.authentification.*;
import com.feelhub.web.representation.ModelAndView;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class HomeResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        //apiFeelingSearch = mock(ApiFeelingSearch.class);
        //homeResource = new HomeResource();
        //ContextTestFactory.initResource(homeResource);
        //CurrentUser.set(new WebUser(TestFactories.users().createActiveUser("test@test.com"), true));
    }

    @Test
    public void hasLocalesInData() {
        final ModelAndView modelAndView = homeResource.represent();

        assertThat(modelAndView.getData("locales")).isNotNull();
    }

    @Test
    public void hasTopicsInData() {
        //final List<FeelingData> initialDatas = Lists.newArrayList();
        //initialDatas.add(new FeelingData.Builder().build());
        //initialDatas.add(new FeelingData.Builder().build());
        //when(apiFeelingSearch.doSearch(any(Form.class))).thenReturn(initialDatas);

        final ModelAndView modelAndView = homeResource.represent();

        assertThat(modelAndView.getData("topicDatas")).isNotNull();
        //final List<FeelingData> result = modelAndView.getData("feelingDatas");
        //assertThat(result.size()).isEqualTo(2);
    }

    private HomeResource homeResource;
}
