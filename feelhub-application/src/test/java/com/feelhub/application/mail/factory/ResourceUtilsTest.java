package com.feelhub.application.mail.factory;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class ResourceUtilsTest {

    @Test
    public void canGetFileContent() {
        final String content = ResourceUtils.resource("template-test.ftl");

        assertThat(content).isEqualTo("a simple template ${withData}\nwith multi line\n");
    }
}
