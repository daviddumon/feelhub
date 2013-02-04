package com.feelhub.application.mail.factory;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class TestsResourceUtils {

    @Test
    public void canGetFileContent() {
        String content = ResourceUtils.resource("template-test.ftl");

        assertThat(content).isEqualTo("a simple template ${withData}\nwith multi line\n");
    }
}
