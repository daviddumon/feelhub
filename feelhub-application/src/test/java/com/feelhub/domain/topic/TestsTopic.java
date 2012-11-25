package com.feelhub.domain.topic;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.test.SystemTime;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsTopic {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        id = UUID.randomUUID();
        topic = new Topic(id);
    }

    @Test
    public void canCreateATopic() {
        assertThat(topic.getId()).isNotNull();
        assertThat(topic.getId()).isEqualTo(id);
        assertThat(topic.getCreationDate()).isEqualTo(time.getNow());
        assertThat(topic.getCreationDate()).isEqualTo(topic.getLastModificationDate());
    }

    @Test
    public void canSetLastModificationDate() {
        time.waitHours(1);

        topic.setLastModificationDate(new DateTime());

        assertThat(topic.getLastModificationDate()).isEqualTo(time.getNow());
    }

    @Test
    public void canSetType() {
        topic.setType(TopicType.World);

        assertThat(topic.getType()).isEqualTo(TopicType.World);
    }

    @Test
    public void hasSubTypes() {
        assertThat(topic.getSubTypes()).isNotNull();
        assertThat(topic.getSubTypes().size()).isZero();
    }

    @Test
    public void canAddASubType() {
        final String subtype = "subtype";

        topic.addSubType(subtype);

        assertThat(topic.getSubTypes().size()).isEqualTo(1);
        assertThat(topic.getSubTypes().get(0)).isEqualTo(subtype);
    }

    @Test
    public void hasUrls() {
        assertThat(topic.getUrls()).isNotNull();
        assertThat(topic.getUrls().size()).isZero();
    }

    @Test
    public void canAddAnUrl() {
        final String url = "http://www.url.com";

        topic.addUrl(url);

        assertThat(topic.getUrls().size()).isEqualTo(1);
        assertThat(topic.getUrls().get(0)).isEqualTo(url);
    }

    @Test
    public void canAddDescription() {
        final String referenceDescription = "description-reference";

        topic.addDescription(FeelhubLanguage.reference(), referenceDescription);

        assertThat(topic.getDescriptions()).isNotNull();
        assertThat(topic.getDescriptions().size()).isEqualTo(1);
    }

    @Test
    public void canReturnGoodDescription() {
        final String referenceDescription = "description-reference";
        final String frDescription = "description-fr";
        topic.addDescription(FeelhubLanguage.reference(), referenceDescription);
        topic.addDescription(FeelhubLanguage.fromCode("fr"), frDescription);

        assertThat(topic.getDescription(FeelhubLanguage.reference())).isEqualTo(referenceDescription);
        assertThat(topic.getDescription(FeelhubLanguage.fromCode("fr"))).isEqualTo(frDescription);
        assertThat(topic.getDescription(FeelhubLanguage.fromCode("es"))).isEqualTo(referenceDescription);
    }

    private UUID id;
    private Topic topic;
}
