package com.steambeat.domain.translation;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.steambeat.domain.thesaurus.SteambeatLanguage;

public class Translator {

    public Translator() {
        Translate.setClientId("steambeat");
        Translate.setClientSecret("XrLNktvCKuAOe12+TUOLWwJuZiju+5iCwvMRCLGpB8Q=");
    }

    public String translateToEnglish(final String value, final SteambeatLanguage steambeatLanguage) throws Exception {
        return Translate.execute(value, steambeatLanguage.getMicrosoftLanguage(), Language.ENGLISH);
    }
}
