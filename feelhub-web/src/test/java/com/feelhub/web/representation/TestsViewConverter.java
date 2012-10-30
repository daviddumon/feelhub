package com.feelhub.web.representation;

import freemarker.template.Configuration;
import org.junit.*;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;

import java.io.IOException;
import java.util.Map;

import static org.fest.assertions.Assertions.*;
import static org.fest.assertions.MapAssert.*;

public class TestsViewConverter {

    @After
    public void tearDown() throws Exception {
        Context.setCurrent(null);
    }

    @Before
    public void setUp() throws Exception {
        modelAndViewConverter = new ModelAndViewConverter();
    }

    @Test
    public void canScore() {
        final float score = modelAndViewConverter.score(ModelAndView.createNew("test.ftl"), null, null);

        assertThat(score).isEqualTo(1.0f);
    }

    @Test
    public void canConvertToRepresentation() throws IOException {
        setupFreemarker();
        final ModelAndView modelAndView = ModelAndView.createNew("test.ftl").with("test", "test");

        final TemplateRepresentation representation = (TemplateRepresentation) modelAndViewConverter.toRepresentation(modelAndView, null, null);

        assertThat(representation).isNotNull();
        assertThat(representation.getMediaType()).isEqualTo(MediaType.TEXT_HTML);
        final Map<String, Object> dataModel = (Map<String, Object>) representation.getDataModel();
        assertThat(dataModel).includes(entry("test", "test"));
        assertThat(representation.getTemplate().getName()).isEqualTo("test.ftl");
    }

    private void setupFreemarker() {
        final Context context = new Context();
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(getClass(), "/");
        context.getAttributes().put("org.freemarker.Configuration", configuration);
        Context.setCurrent(context);
    }

    private ModelAndViewConverter modelAndViewConverter;
}
