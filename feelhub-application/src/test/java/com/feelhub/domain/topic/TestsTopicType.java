package com.feelhub.domain.topic;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsTopicType {

    @Test
    public void canGetListOfUsableValues() {
        final List<TopicType> types = TopicType.usableValues();

        assertThat(types.contains(TopicType.World)).isFalse();
        assertThat(types.contains(TopicType.None)).isFalse();
    }
}
