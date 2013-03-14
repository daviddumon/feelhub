package com.feelhub.patch;

import com.feelhub.repositories.SessionProvider;
import org.slf4j.*;

public final class Main {

    private Main() {
    }

    public static void main(final String[] args) {
        LOGGER.info("Patch begin");
        new PatchCloudinary(new SessionProvider()).execute();
        LOGGER.info("Patch end");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
}
