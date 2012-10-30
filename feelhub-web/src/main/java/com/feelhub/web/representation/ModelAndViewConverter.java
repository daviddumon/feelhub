package com.feelhub.web.representation;

import com.google.common.collect.Lists;
import freemarker.template.Configuration;
import org.restlet.Context;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.engine.resource.VariantInfo;
import org.restlet.representation.*;
import org.restlet.resource.UniformResource;

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
    public float score(final Object o, final Variant variant, final UniformResource uniformResource) {
        if (ModelAndView.class.isAssignableFrom(o.getClass())) {
            return 1.0f;
        }
        return -1.0f;
    }

    @Override
    public <T> float score(final Representation representation, final Class<T> tClass, final UniformResource uniformResource) {
        return -1.0f;
    }

    @Override
    public <T> T toObject(final Representation representation, final Class<T> tClass, final UniformResource uniformResource) throws IOException {
        return null;
    }

    @Override
    public Representation toRepresentation(final Object o, final Variant variant, final UniformResource uniformResource) throws IOException {
        ModelAndView modelAndView = (ModelAndView) o;
        return new org.restlet.ext.freemarker.TemplateRepresentation(modelAndView.getTemplate(), getConfiguration(), modelAndView.getData(), modelAndView.getType());
    }

    private Configuration getConfiguration() {
        return (Configuration) Context.getCurrent().getAttributes().get("org.freemarker.Configuration");
    }
}
