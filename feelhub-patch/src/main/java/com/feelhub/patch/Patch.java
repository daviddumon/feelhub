package com.feelhub.patch;

import com.feelhub.repositories.MongoRepositories;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.SessionProvider;
import org.apache.log4j.Logger;

public abstract class Patch {

    public Patch(SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public void execute() {
        try {
            //patchBdd();
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

    //private void patchBdd() {
    //    executeScripts("scripts", ConfigurationBase.chaîneConnexionBailleur());
    //}

    //private void executeScripts(final String cheminScripts) {
    //    final Script script = new Script(cheminScripts, version());
    //    script.exécute();
    //}

    public abstract Version version();

    private SessionProvider sessionProvider;
    private static final Logger LOGGER = Logger.getLogger(Patch.class);
}
