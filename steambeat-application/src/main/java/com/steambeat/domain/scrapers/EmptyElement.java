package com.steambeat.domain.scrapers;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class EmptyElement extends Element {

    public EmptyElement() {
        super(Tag.valueOf("emptyelement"), "");
    }
}
