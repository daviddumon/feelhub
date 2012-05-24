package com.steambeat.domain.bingsearch;

import com.steambeat.domain.subject.concept.Concept;

import java.net.URLEncoder;

public class BingLink {

    public String getIllustration(final Concept concept) {
        final String query = getQuery(concept);
        return "";
    }

    public String getQuery(final Concept concept) {
        try {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(queryRoot);
            stringBuilder.append(concept.getDescription());
            stringBuilder.append(queryOptions);
            return URLEncoder.encode(stringBuilder.toString(), "UTF-8");
        } catch (Exception e) {
        }
        return "";
    }


    private static String queryRoot = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/Image?Query='";
    private static String queryOptions = "'&Adult='Off'&ImageFilters='Size:Medium'&$top=1&$format=JSON";
    private static String clientId = "steambeat";
    private static String ClientSecret = "XrLNktvCKuAOe12+TUOLWwJuZiju+5iCwvMRCLGpB8Q=";
}
