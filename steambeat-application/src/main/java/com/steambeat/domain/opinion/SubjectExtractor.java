package com.steambeat.domain.opinion;

import com.google.common.collect.*;
import com.steambeat.application.KeywordService;

import java.util.*;

public class SubjectExtractor {

    public List<Subject> extract(final String text) {
        List<Subject> subjects = Lists.newArrayList();
        final String[] tokens = text.split("\\s");
        for (String token : tokens) {
            final TreeMap<Integer, Feeling> tokenTags = getSemanticTags(token);
            if (hasAny(tokenTags)) {
                Feeling tokenFeeling = getFeeling(tokenTags);
                final String cleanedToken = token.replaceAll(STRING_REPLACE, "").toLowerCase();
                if (cleanedToken.length() > 2) {
                    final Subject subject = new Subject(tokenFeeling, cleanedToken);
                    subjects.add(subject);
                }
            } else if (checkGrammar(token)) {
                final String cleanedToken = token.trim().toLowerCase();
                if (cleanedToken.length() > 2) {
                    final Subject subject = new Subject(Feeling.none, cleanedToken);
                    subjects.add(subject);
                }
            }
        }
        return subjects;
    }

    private boolean checkGrammar(final String token) {
        return KeywordService.isUri(token);
    }

    private TreeMap<Integer, Feeling> getSemanticTags(final String token) {
        TreeMap<Integer, Feeling> counts = Maps.newTreeMap();
        counts.put(token.lastIndexOf("#"), Feeling.none);
        counts.put(token.lastIndexOf("-"), Feeling.bad);
        counts.put(token.lastIndexOf("="), Feeling.neutral);
        counts.put(token.lastIndexOf("+"), Feeling.good);
        return counts;
    }

    private boolean hasAny(final TreeMap<Integer, Feeling> map) {
        return map.lastEntry().getKey() >= 0;
    }

    private Feeling getFeeling(final TreeMap<Integer, Feeling> map) {
        return map.lastEntry().getValue();
    }

    private static final String STRING_REPLACE = "\\W";
}
