package com.steambeat.web;

import org.restlet.data.*;
import org.restlet.resource.*;

public class OpinionsResource extends ServerResource {

    @Post
    public void post(final Form form) {
        final String text = form.getFirstValue("text");
        //List<Judgment> judgments = Lists.newArrayList();
        //final ListIterator<Parameter> parameterListIterator = form.listIterator();
        //while (parameterListIterator.hasNext()) {
        //    final Parameter next = parameterListIterator.next();
        //    judgments.add(new Judgment(next.getFirst(), Feeling.valueOf(next.getSecond())));
        //}
        setStatus(Status.SUCCESS_CREATED);
    }
}
