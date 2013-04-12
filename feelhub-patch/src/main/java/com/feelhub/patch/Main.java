package com.feelhub.patch;

import com.feelhub.analytic.AnalyticWorkersModule;
import com.feelhub.domain.DomainWorkersModule;
import com.feelhub.repositories.SessionProvider;
import com.google.inject.*;
import org.slf4j.*;

public final class Main {

    private Main() {
    }

    public static void main(final String[] args) {
        Guice.createInjector(Stage.PRODUCTION, new GuicePatchModule(), new DomainWorkersModule(), new AnalyticWorkersModule());
        LOGGER.info("Patch begin");
        new Patch_2013_04_12_1(new SessionProvider()).execute();
        LOGGER.info("Patch end");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
}
