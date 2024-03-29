package com.feelhub.patch;

import com.feelhub.repositories.*;
import org.slf4j.*;

public abstract class Patch {

    public Patch(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public void execute() {
        try {
            initializeRepositories();
            if (withBusinessPatch()) {
                patchBusiness();
            }
        } catch (final ScriptExecutionException e) {
            LOGGER.error("Error with patch", e);
        }
    }

    protected abstract boolean withBusinessPatch();

    protected void initializeRepositories() {
        sessionProvider.init();
        Repositories.initialize(new MongoRepositories(sessionProvider));
    }

    private void patchBusiness() {
        LOGGER.info("Business Patch START");
        try {
            sessionProvider.start();
            doBusinessPatch();
            sessionProvider.stop();
            LOGGER.info("Business Patch OK");
        } catch (final Exception e) {
            LOGGER.error("Business Patch ERROR", e);
            throw new ScriptExecutionException(e);
        } finally {
            sessionProvider.close();
        }
    }

    protected abstract void doBusinessPatch();

    public abstract Version version();

    protected SessionProvider sessionProvider;
    protected static final Logger LOGGER = LoggerFactory.getLogger(Patch.class);
}
