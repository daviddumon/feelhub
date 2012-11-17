package com.feelhub.domain.thesaurus;

import com.memetix.mst.language.Language;

public class NullFeelhubLanguage extends FeelhubLanguage {
    public NullFeelhubLanguage() {
        super("none");
    }

    @Override
    public Language getMicrosoftLanguage() {
        return Language.AUTO_DETECT;
    }

}
