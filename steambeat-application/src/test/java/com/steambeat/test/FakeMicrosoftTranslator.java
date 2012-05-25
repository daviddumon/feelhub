package com.steambeat.test;

import com.steambeat.domain.translation.MicrosoftTranslator;

public class FakeMicrosoftTranslator extends MicrosoftTranslator {

    @Override
    public String translate(final String text, final String from) {
        return text;
    }
}
