package com.feelhub.web.resources;

import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.representation.ModelAndView;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

public class SearchResourceTest {

    @Test
    public void canRenderPage() {
        SearchResource resource = new SearchResource();
        ContextTestFactory.initResource(resource);
        resource.getRequest().setResourceRef("/search?q=arpinum");

        ModelAndView result =  resource.represent();

        assertThat(result).isNotNull();
        assertThat(result.getTemplate()).isEqualTo("search.ftl");
        assertThat(result.getData()).includes(entry("query", "arpinum"));
    }
}
