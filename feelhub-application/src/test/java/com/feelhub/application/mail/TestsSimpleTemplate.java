package com.feelhub.application.mail;

import com.feelhub.application.mail.factory.ResourceUtils;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class TestsSimpleTemplate {

    @Test
    public void canGetString() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("withData", "toto");

        String result = new SimpleTemplate(ResourceUtils.resource("template-test.ftl")).apply(data);

        assertThat(result).startsWith("a simple template toto");
    }
}
