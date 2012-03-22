package com.steambeat.sitemap.test;

import com.steambeat.sitemap.domain.RobotsFile;
import org.junit.rules.ExternalResource;

public class WithRobotFile extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        RobotsFile.INSTANCE.clear();
    }
}
