package com.steambeat.web;

import com.google.common.collect.Lists;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.subject.feed.Feed;
import com.steambeat.web.search.OpinionSearch;
import org.restlet.Context;

import java.util.List;

public class Page {

    public static List<Page> forContextAndFeed(final Context context, final Feed feed, OpinionSearch opinionSearch) {
        final List<Page> pages = Lists.newArrayList();
        final List<Opinion> feedOpinions = opinionSearch.forSubject(feed);
        final List<List<Opinion>> partitions = Lists.partition(Lists.reverse(feedOpinions), 20);
        int count = 1;
        for (final List<Opinion> opinions : partitions) {
            final Page page = new Page();
            for (Opinion opinion : opinions) {
                opinion.setText(Page.parse(feed, opinion.getText(), context));
            }
            page.opinions = opinions;
            page.number = count;
            page.max = feedOpinions.size();
            count++;
            pages.add(page);
        }
        return pages;
    }

    public static String parse(final Feed feed, final String value, final Context context) {
        if (value != null) {
            String[] tokens = value.split(" ");
            final StringBuilder stringBuilder = new StringBuilder();
            for (String token : tokens) {
                if (token.matches("@[0-9]*")) {
                    token = transform(feed, token, context);
                }
                stringBuilder.append(token + " ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return stringBuilder.toString();
        }
        return null;
    }

    private static String transform(final Feed feed, final String token, final Context context) {
        final int opinionIndex = Integer.valueOf(token.substring(1, token.length()));
        final String link = "<a href=\""
                + new ReferenceBuilder(context).buildUri("/feeds/" + feed.getUri() + "/opinions/" + opinionIndex)
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
