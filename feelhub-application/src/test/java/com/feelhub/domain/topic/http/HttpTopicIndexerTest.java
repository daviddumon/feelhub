package com.feelhub.domain.topic.http;

import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.*;
import org.restlet.data.MediaType;

import java.util.*;

import static org.fest.assertions.Assertions.*;

public class HttpTopicIndexerTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canIndexFromResolverResult() {
        final ResolverResult resolverResult = new ResolverResult();
        resolverResult.setMediaType(MediaType.TEXT_HTML);
        resolverResult.addUriToPath(new Uri("http://www.fakeurl.com"));
        final HttpTopic topic = new HttpTopic(UUID.randomUUID());

        new HttpTopicIndexer().index(topic, resolverResult);

        final List<Tag> tags = Repositories.tags().getAll();
        assertThat(tags.size()).isEqualTo(4);
    }
}
