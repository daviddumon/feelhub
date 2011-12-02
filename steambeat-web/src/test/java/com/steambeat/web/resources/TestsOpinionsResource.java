package com.steambeat.web.resources;

import com.google.common.collect.Lists;
import com.steambeat.domain.opinion.Feeling;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.SystemTime;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canPostAnOpinion() {
        final Form form = getGoodForm();
        final ClientResource clientResource = restlet.newClientResource("/opinions");

        clientResource.post(form);

        assertThat(clientResource.getStatus(), is(Status.SUCCESS_CREATED));
        //assertThat(Repositories.opinions().getAll().size(), is(1));
    }

    private Form getGoodForm() {
        final Form form = new Form();
        form.add("text", "my opinion");
        List<Parameter> judgments = Lists.newArrayList();
        judgments.add(new Parameter("http://www.fake.com", Feeling.good.toString()));
        form.addAll(judgments);
        return form;
    }
}
