package com.feelhub.domain.keyword;

import java.util.regex.Pattern;

public class KeywordIdentifier {

    public static boolean isUri(final String text) {
        return URI_PATTERN.matcher(text).matches();
    }

    private static final Pattern URI_PATTERN = Pattern.compile("((http|https)://)?([%a-zA-Z_0-9\\.-]+)(\\.[a-z]{2,3}){1}(/.*$)?", Pattern.CASE_INSENSITIVE);
}
