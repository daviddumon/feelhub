package com.steambeat.domain.opinion;

import com.google.common.collect.*;
import com.steambeat.application.KeywordService;

import java.util.*;

public class SubjectExtractor {

    public List<Subject> extract(final String text) {
        final List<Subject> subjects = Lists.newArrayList();
        final String[] tokens = text.split("\\s");
        for (final String token : tokens) {
            final TreeMap<Integer, Feeling> tokenTags = getSemanticTags(token);
            if (hasAny(tokenTags)) {
                final Feeling tokenFeeling = getFeeling(tokenTags);
                String cleanedToken = token.replaceAll(STRING_REPLACE_SEMANTIC, "").toLowerCase();
                if (!isUri(cleanedToken)) {
                    cleanedToken = token.replaceAll(STRING_REPLACE_ALL, "").toLowerCase();
                    cleanedToken = cleanedToken.replaceAll(STRING_TO_SPACES, " ");
                }
                if (cleanedToken.length() > 2) {
                    final Subject subject = new Subject(tokenFeeling, cleanedToken);
                    subjects.add(subject);
                }
            } else if (isUri(token)) {
                final String cleanedToken = token.trim().toLowerCase();
                if (cleanedToken.length() > 2) {
                    final Subject subject = new Subject(Feeling.none, cleanedToken);
                    subjects.add(subject);
                }
            }
        }
        return subjects;
    }

    private TreeMap<Integer, Feeling> getSemanticTags(final String token) {
        final TreeMap<Integer, Feeling> counts = Maps.newTreeMap();
        counts.put(token.lastIndexOf("#"), Feeling.none);
        counts.put(token.lastIndexOf("-"), Feeling.bad);
        counts.put(token.lastIndexOf("="), Feeling.neutral);
        counts.put(token.lastIndexOf("+"), Feeling.good);
        return counts;
    }

    private boolean isUri(final String token) {
        return KeywordService.isUri(token);
    }

    private boolean hasAny(final TreeMap<Integer, Feeling> map) {
        return map.lastEntry().getKey() >= 0;
    }

    private Feeling getFeeling(final TreeMap<Integer, Feeling> map) {
        return map.lastEntry().getValue();
    }

    private static final String STRING_REPLACE_ALL = "[\\p{Punct}&&[^\\_^\\']]";
    private static final String STRING_REPLACE_SEMANTIC = "[\\+\\-\\=\\#]";
    private static final String STRING_TO_SPACES = "[\\_]";
}
