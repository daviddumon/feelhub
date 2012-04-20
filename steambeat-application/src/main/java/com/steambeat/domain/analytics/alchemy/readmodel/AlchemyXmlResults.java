package com.steambeat.domain.analytics.alchemy.readmodel;

import com.google.common.collect.Lists;
import org.simpleframework.xml.*;

import java.util.List;

@Root
public class AlchemyXmlResults {

    public AlchemyXmlResults() {
        status = "";
        usage = "";
        url = "";
        language = "";
        entities = Lists.newArrayList();
    }

    @Element
    public String status;

    @Element(required = false)
    public String usage;

    @Element(required = false)
    public String url;

    @Element
    public String language;

    @ElementList(required = false)
    public List<AlchemyXmlEntity> entities;
}
