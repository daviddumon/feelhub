package com.feelhub.domain.translation;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class Translator {

    public Translator() {
        Translate.setClientId("steambeat");
        Translate.setClientSecret("XrLNktvCKuAOe12+TUOLWwJuZiju+5iCwvMRCLGpB8Q=");
    }

    public String translateToEnglish(final String value, final FeelhubLanguage feelhubLanguage) throws Exception {
        return Translate.execute(value, feelhubLanguage.getMicrosoftLanguage(), Language.ENGLISH);
    }
}
