package com.steambeat.domain.translation;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class MicrosoftTranslator {

    public MicrosoftTranslator() {
        Translate.setClientId("steambeat");
        Translate.setClientSecret("XrLNktvCKuAOe12+TUOLWwJuZiju+5iCwvMRCLGpB8Q=");
    }

    public String translate(final String text, final Language from) {
        try {
            return Translate.execute(text, from, Language.ENGLISH);
        } catch (Exception e) {
            throw new TranslateException();
        }
    }
}
