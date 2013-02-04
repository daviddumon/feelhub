package com.feelhub.patch;

import org.apache.log4j.Logger;

public final class Main {
	private Main() {
	}

	public static void main(final String[] args) {
		LOGGER.info("Patch begin");
        new PatchTest(null).execute();
    	LOGGER.info("Patch end");
	}

	private static final Logger LOGGER = Logger.getLogger(Main.class);
}
