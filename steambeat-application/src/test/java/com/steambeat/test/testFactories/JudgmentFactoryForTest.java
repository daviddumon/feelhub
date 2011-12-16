package com.steambeat.test.testFactories;

import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.webpage.WebPage;

public class JudgmentFactoryForTest {

    public Judgment newJudgment() {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        return webPage.createJudgment(Feeling.good);
    }
}
