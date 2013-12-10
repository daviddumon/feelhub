package com.feelhub.web.representation;

import com.google.common.collect.Lists;
import freemarker.template.Configuration;
import org.restlet.Context;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.engine.resource.VariantInfo;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.Resource;

import java.io.IOException;
import java.util.List;

public class ModelAndViewConverter extends ConverterHelper {

    @Override
    public List<Class<?>> getObjectClasses(final Variant variant) {
        return Lists.newArrayList();
    }

    @Override
    public List<VariantInfo> getVariants(final Class<?> aClass) {
        return Lists.newArrayList();
    }

    @Override
    public float score(Object o, Variant variant, Resource resource) {
        if (ModelAndView.class.isAssignableFrom(o.getClass())) {
            return 1.0f;
        }
        return -1.0f;
    }

    @Override
    public <T> float score(Representation representation, Class<T> tClass, Resource resource) {
        return -1.0f;
    }

    @Override
    public <T> T toObject(Representation representation, Class<T> tClass, Resource resource) throws IOException {
        return null;
    }

    @Override
    public Representation toRepresentation(Object o, Variant variant, Resource resource) throws IOException {
        final ModelAndView modelAndView = (ModelAndView) o;
        return new org.restlet.ext.freemarker.TemplateRepresentation(modelAndView.getTemplate(), getConfiguration(), modelAndView.getData(), modelAndView.getType());
    }

    private Configuration getConfiguration() {
        return (Configuration) Context.getCurrent().getAttributes().get("org.freemarker.Configuration");
    }
}
