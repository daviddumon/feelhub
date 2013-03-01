package com.feelhub.domain.scraper;

import com.google.common.collect.Lists;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.util.List;

public class JsoupGroupAttributExtractor {

    List<String> parse(final Document document, final String tag, final String attribut) {
        final List<String> result = Lists.newArrayList();
        final Elements elements = document.select(tag);
        for (final Element element : elements) {
            result.add(element.attr(attribut));
        }
        return result;
    }
}
