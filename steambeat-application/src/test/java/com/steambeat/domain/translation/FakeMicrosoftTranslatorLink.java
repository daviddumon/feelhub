package com.steambeat.domain.translation;

import com.memetix.mst.language.Language;

public class FakeMicrosoftTranslatorLink extends MicrosoftTranslatorLink {

    @Override
    public String translate(final String text, final Language from) {
        return text;
    }
}
