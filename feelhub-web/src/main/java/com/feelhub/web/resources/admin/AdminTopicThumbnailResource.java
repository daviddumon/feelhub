package com.feelhub.web.resources.admin;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.topic.UpdateThumbnailTopicCommand;
import com.google.inject.Inject;
import org.restlet.data.Form;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.util.UUID;

public class AdminTopicThumbnailResource extends ServerResource {

    @Inject
    public AdminTopicThumbnailResource(CommandBus bus) {
        this.bus = bus;
    }

    @Put
    public void put(Form form) {
        bus.execute(new UpdateThumbnailTopicCommand(extractTopicId()));
    }

    private UUID extractTopicId() {
        return UUID.fromString(getRequestAttributes().get("topicId").toString());
    }

    private CommandBus bus;
}
