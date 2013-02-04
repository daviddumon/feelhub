package com.feelhub.domain.alchemy;

import com.feelhub.domain.admin.*;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.test.SystemTime;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsAlchemyLink {

    @Rule
    public SystemTime systemTime = SystemTime.fixed();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void incrementStatistic() {
        bus.capture(ApiCallEvent.class);
        FakeAlchemyLink alchemyLink = new FakeAlchemyLink();

        alchemyLink.get(new Uri("http://www.toto.com"));

        ApiCallEvent event = bus.lastEvent(ApiCallEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.getApi()).isEqualTo(Api.Alchemy);
        assertThat(event.getIncrement()).isEqualTo(1);
    }

}
