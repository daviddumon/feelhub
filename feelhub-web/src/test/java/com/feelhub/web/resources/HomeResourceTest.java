package com.feelhub.web.resources;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.authentification.WebUser;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.ApiFeelingSearch;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.restlet.data.Form;
import org.restlet.data.Reference;
import org.restlet.data.Status;

import java.util.List;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

//@Ignore("ils me petent les couilles les tests web")
// ils péteraient pas les couilles s'ils étaient bien faits. C'était quoi cette horreur d'implémentation et de tests ??!?
public class HomeResourceTest {


    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        apiFeelingSearch = mock(ApiFeelingSearch.class);
        homeResource = new HomeResource(apiFeelingSearch);
        ContextTestFactory.initResource(homeResource);
        CurrentUser.set(new WebUser(TestFactories.users().createActiveUser("test@test.com"), true));
    }

    @Test
    public void hasLocalesInData() {
        final ModelAndView modelAndView = homeResource.represent();

        assertThat(modelAndView.getData("locales")).isNotNull();
    }

    @Test
    public void hasFeelingDatasInModel() {
        final List<FeelingData> initialDatas = Lists.newArrayList();
        initialDatas.add(new FeelingData.Builder().build());
        initialDatas.add(new FeelingData.Builder().build());
        when(apiFeelingSearch.doSearch(any(Form.class))).thenReturn(initialDatas);

        final ModelAndView modelAndView = homeResource.represent();

        assertThat(modelAndView.getData("feelingDatas")).isNotNull();
        final List<FeelingData> result = modelAndView.getData("feelingDatas");
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void redirectsOnAnonymousUser() {
        CurrentUser.set(WebUser.anonymous());

        homeResource.represent();

        assertThat(homeResource.getStatus()).isEqualTo(Status.REDIRECTION_TEMPORARY);
        assertThat(homeResource.getLocationRef()).isEqualTo(new Reference("https://thedomain//signup"));
    }

    private HomeResource homeResource;
    private ApiFeelingSearch apiFeelingSearch;
}
