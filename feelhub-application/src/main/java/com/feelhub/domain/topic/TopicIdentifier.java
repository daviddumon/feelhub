package com.feelhub.domain.topic;

import java.util.regex.Pattern;

public class TopicIdentifier {

    public static boolean isWebTopic(final String text) {
        return URI_PATTERN.matcher(text).matches();
    }

    private static final Pattern URI_PATTERN = Pattern.compile("((ftp|http|https)://)?([%a-zA-Z_0-9\\.-]+)(\\.[a-z]{2,3}){1}(/.*$)?", Pattern.CASE_INSENSITIVE);
}
