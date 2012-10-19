package com.steambeat.domain.translation;

import com.steambeat.domain.thesaurus.SteambeatLanguage;

public class FakeTranslator extends Translator {

    @Override
    public String translateToEnglish(final String value, final SteambeatLanguage steambeatLanguage) throws Exception {
        if (value.equals("Exception")) {
            throw new Exception();
        }
        return value + "english";
    }
}
