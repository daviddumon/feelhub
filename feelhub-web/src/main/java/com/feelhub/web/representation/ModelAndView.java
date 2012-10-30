package com.feelhub.web.representation;

import com.google.common.collect.Maps;
import org.restlet.data.MediaType;

import java.util.Map;

public class ModelAndView {

    public static ModelAndView createNew(final String template, final MediaType mediaType) {
        return new ModelAndView(template, mediaType);
    }

    public static ModelAndView createNew(final String template) {
        return new ModelAndView(template, MediaType.TEXT_HTML);
    }

    private ModelAndView(final String template, final MediaType type) {
        this.template = template;
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public <T> T getData(String key) {
        return (T) data.get(key);
    }

    public MediaType getType() {
        return type;
    }

    public ModelAndView with(final String key, final Object value) {
        data.put(key, value);
        return this;
    }

    private final String template;
    private final Map<String, Object> data = Maps.newConcurrentMap();
    private final MediaType type;
}
