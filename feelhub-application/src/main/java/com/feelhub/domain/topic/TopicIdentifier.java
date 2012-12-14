package com.feelhub.domain.topic;

import java.util.regex.Pattern;

public class TopicIdentifier {

    public static boolean isHttpTopic(final String text) {
        return HTTP_PATTERN.matcher(text).matches();
    }

    public static boolean isFtpTopic(final String text) {
        return FTP_PATTERN.matcher(text).matches();
    }

    private static final Pattern HTTP_PATTERN = Pattern.compile("((http|https)://)?([%a-zA-Z_0-9\\.-]+)(\\.[a-z]{2,3}){1}(/.*$)?", Pattern.CASE_INSENSITIVE);
    private static final Pattern FTP_PATTERN = Pattern.compile("((ftp|ftps)://)?([%a-zA-Z_0-9\\.-]+)(\\.[a-z]{2,3}){1}(/.*$)?", Pattern.CASE_INSENSITIVE);
}
