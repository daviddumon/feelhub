package com.steambeat.domain.opinion;

import com.steambeat.test.SystemTime;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TestsOpinionPostedEvent {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void hasADate() {
        final OpinionPostedEvent event = new OpinionPostedEvent(null, null);

        assertThat(event.getDate(), is(time.getNow()));
    }

}
