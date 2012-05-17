package com.steambeat.domain.translation;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class MicrosoftTranslator implements Translator {

    public MicrosoftTranslator() {
        Translate.setClientId("steambeat");
        Translate.setClientSecret("XrLNktvCKuAOe12+TUOLWwJuZiju+5iCwvMRCLGpB8Q=");
    }

    @Override
    public String translate(final String text, final String from) {
        try {
            Language language = getLanguage(from);
            return Translate.execute(text, language, Language.ENGLISH);
        } catch (Exception e) {
            return "";
        }
    }

    private Language getLanguage(final String from) throws Exception {
        if (from.equalsIgnoreCase("French")) {
            return Language.FRENCH;
        } else if (from.equalsIgnoreCase("German")) {
            return Language.GERMAN;
        } else if (from.equalsIgnoreCase("Italian")) {
            return Language.ITALIAN;
        } else if (from.equalsIgnoreCase("Portuguese")) {
            return Language.PORTUGUESE;
        } else if (from.equalsIgnoreCase("Russian")) {
            return Language.RUSSIAN;
        } else if (from.equalsIgnoreCase("Spanish")) {
            return Language.SPANISH;
        } else if (from.equalsIgnoreCase("Swedish")) {
            return Language.SWEDISH;
        } else {
            throw new Exception("language unknown");
        }
    }
}
