package com.steambeat.web;

import com.google.common.collect.Lists;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.web.search.OpinionSearch;
import org.restlet.Context;

import java.util.List;

public class Page {

    public static List<Page> forContextAndSubject(final Context context, final WebPage webPage, final OpinionSearch opinionSearch) {
        final List<Page> pages = Lists.newArrayList();
        final List<Opinion> webPageOpinions = opinionSearch.forSubject(webPage);
        final List<List<Opinion>> partitions = Lists.partition(Lists.reverse(webPageOpinions), 20);
        int count = 1;
        for (final List<Opinion> opinions : partitions) {
            final Page page = new Page();
            //for (final Opinion opinion : opinions) {
            //    opinion.setText(Page.parse(webPage, opinion.getText(), context));
            //}
            page.opinions = opinions;
            page.number = count;
            page.max = webPageOpinions.size();
            count++;
            pages.add(page);
        }
        return pages;
    }

    public static String parse(final WebPage webPage, final String value, final Context context) {
        if (value != null) {
            final String[] tokens = value.split(" ");
            final StringBuilder stringBuilder = new StringBuilder();
            for (String token : tokens) {
                if (token.matches("@[0-9]*")) {
                    token = transform(webPage, token, context);
                }
                stringBuilder.append(token + " ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return stringBuilder.toString();
        }
        return null;
    }

    private static String transform(final WebPage webPage, final String token, final Context context) {
        final int opinionIndex = Integer.valueOf(token.substring(1, token.length()));
        final String link = "<a href=\""
                + new ReferenceBuilder(context).buildUri("/webpages/" + webPage.getId() + "/opinions/" + opinionIndex)
                + "\">" + token + "</a>";
        return link;
    }

    public List<Opinion> getOpinions() {
        return opinions;
    }

    public int getNumber() {
        return number;
    }

    public int getMax() {
        return max;
    }

    private List<Opinion> opinions = Lists.newArrayList();
    private int number;
    private int max;
}
