package com.steambeat.test;

import com.steambeat.tools.HtmlParser;

public class FakeHtmlParser extends HtmlParser {

    @Override
    public void parse() {

    }

    @Override
    public String getSingleNode(final String tag) {
        return tag;
    }
}
