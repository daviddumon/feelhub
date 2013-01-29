package com.feelhub.web.search;

import com.feelhub.domain.media.Media;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsMediaSearch extends TestWithMongoRepository {

    @Before
    public void before() {
        mediaSearch = new MediaSearch(getProvider());
    }

    @Test
    public void canGetAMedia() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        TestFactories.medias().newMedia(from.getId(), to.getId());

        final List<Media> medias = mediaSearch.execute();

        assertThat(medias.size()).isEqualTo(1);
    }

    @Test
    public void canGetAMediaForATopic() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        TestFactories.medias().newMedia(from.getId(), to.getId());
        TestFactories.medias().newMediaList(10);

        final List<Media> medias = mediaSearch.withTopicId(from.getId()).execute();

        assertThat(medias.size()).isEqualTo(1);
        assertThat(medias.get(0).getFromId()).isEqualTo(from.getId());
        assertThat(medias.get(0).getToId()).isEqualTo(to.getId());
    }

    @Test
    public void canGetMediaForATopic() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        TestFactories.medias().newMediaList(10, from.getId());
        TestFactories.medias().newMediaList(20);

        final List<Media> medias = mediaSearch.withTopicId(from.getId()).execute();

        assertThat(medias.size()).isEqualTo(10);
    }

    @Test
    public void canLimitResults() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        TestFactories.medias().newMediaList(20);
        TestFactories.medias().newMediaList(10, from.getId());

        final List<Media> medias = mediaSearch.withTopicId(from.getId()).withLimit(5).execute();

        assertThat(medias.size()).isEqualTo(5);
        assertThat(medias.get(0).getFromId()).isEqualTo(from.getId());
        assertThat(medias.get(1).getFromId()).isEqualTo(from.getId());
        assertThat(medias.get(2).getFromId()).isEqualTo(from.getId());
        assertThat(medias.get(3).getFromId()).isEqualTo(from.getId());
        assertThat(medias.get(4).getFromId()).isEqualTo(from.getId());
    }

    @Test
    public void canSkipResults() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        TestFactories.medias().newMediaList(5, from.getId());

        final List<Media> medias = mediaSearch.withSkip(2).execute();

        assertThat(medias.size()).isEqualTo(3);
    }

    private MediaSearch mediaSearch;
}
