package com.steambeat.domain.scrapers.tools;

import com.steambeat.domain.analytics.identifiers.uri.Uri;

import java.io.*;
import java.net.*;
import java.util.regex.*;

public class CSSMiner {

    public CSSMiner(final Uri uri) {
        this.uri = uri;
        try {
            final BufferedReader distantCss = openDistantCss();
            copyCssToFile(distantCss);
            closeDistantCss(distantCss);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedReader openDistantCss() throws IOException {
        final URL url = new URL(uri.toString());
        return new BufferedReader(new InputStreamReader(url.openStream()));
    }

    private void copyCssToFile(final BufferedReader distantCss) {
        final StringBuilder builder = new StringBuilder();
        String line = "";
        try {
            while ((line = distantCss.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        css = builder.toString();
    }

    private void closeDistantCss(final BufferedReader in) throws IOException {
        in.close();
    }

    public String scrap(final String tag) {
        try {
            searchBackgroundValue(tag);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return backgroundValue;
    }

    private void searchBackgroundValue(final String tag) throws IOException {
        final Pattern pattern = Pattern.compile(".*" + tag + ".*\\{(.*)\\}");
        final Matcher matcher = pattern.matcher(css);
        if (matcher.matches()) {
            extractBackgroundURl(matcher.group(matcher.groupCount()));
        }
    }

    private void extractBackgroundURl(final String style) {
        final Pattern pattern = Pattern.compile(".*background-url\\:.*\\((.*)\\).*");
        final Matcher matcher = pattern.matcher(style);
        backgroundValue = matcher.matches() ? matcher.group(matcher.groupCount()) : "";
        backgroundValue = backgroundValue.replaceAll("[\'\"]", "").trim();
    }

    private final Uri uri;
    private String backgroundValue = "";
    private String css = "";
}
