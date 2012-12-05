package com.feelhub.domain.translation;

import com.feelhub.domain.thesaurus.FeelhubLanguage;

public class FakeTranslator extends Translator {

    @Override
    public String translateToReference(final String value, final FeelhubLanguage feelhubLanguage) throws Exception {
        if (value.equals("Exception")) {
            throw new Exception();
        }
        return value + "english";
    }
}
