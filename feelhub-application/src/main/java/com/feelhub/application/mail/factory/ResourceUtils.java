package com.feelhub.application.mail.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ResourceUtils {
    public static String resource(final String path) {
        final BufferedReader reader = open(path);
        if (reader == null) {
            return "";
        }
        final StringBuffer buffer = new StringBuffer();
        String currentLine = line(reader);
        while (currentLine != null) {
            buffer.append(currentLine).append('\n');
            currentLine = line(reader);
        }
        try {
            reader.close();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return buffer.toString();
    }

    private static BufferedReader open(final String path) {
        try {
            return new BufferedReader(new InputStreamReader(getClassLoader().getResourceAsStream(path), "UTF8"));
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String line(final BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
