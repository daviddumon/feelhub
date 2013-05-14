package com.feelhub.web.resources;

import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.web.dto.FeelhubMessage;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.ApiFeelingSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

public class ErrorResource extends HomeResource {

    @Inject
    public ErrorResource(final ApiFeelingSearch apiFeelingSearch) {
        super(apiFeelingSearch);
    }

    @Get
    public ModelAndView represent() {
        final ModelAndView modelAndView = super.represent();
        return modelAndView.with("messages", Lists.newArrayList(getErrorMessage()));
    }

    private FeelhubMessage getErrorMessage() {
        final FeelhubMessage feelhubMessage = new FeelhubMessage();
        feelhubMessage.setFeeling(SentimentValue.bad.toString());
        feelhubMessage.setSecondTimer(3);
        feelhubMessage.setText("There was a disturbance in the Force!");
        return feelhubMessage;
    }
}
