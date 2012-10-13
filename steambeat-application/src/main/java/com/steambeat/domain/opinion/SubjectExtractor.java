package com.steambeat.domain.opinion;

import com.google.common.collect.*;

import java.util.*;

public class SubjectExtractor {

    public List<Subject> extract(final String text) {
        List<Subject> subjects = Lists.newArrayList();
        final String[] tokens = text.split("\\s");
        for (String token : tokens) {
            final TreeMap<Integer, Feeling> tokenResults = analyze(token);
            if (isASubject(tokenResults)) {
                Feeling tokenFeeling = getFeeling(tokenResults);
                final String cleanedToken = token.replaceAll(STRING_REPLACE, "").toLowerCase();
                if (cleanedToken.length() > 1) {
                    final Subject subject = new Subject(tokenFeeling, cleanedToken);
                    subjects.add(subject);
                }
            }
        }
        return subjects;
    }

    private TreeMap<Integer, Feeling> analyze(final String token) {
        TreeMap<Integer, Feeling> counts = Maps.newTreeMap();
        counts.put(token.lastIndexOf("#"), Feeling.none);
        counts.put(token.lastIndexOf("-"), Feeling.bad);
        counts.put(token.lastIndexOf("="), Feeling.neutral);
        counts.put(token.lastIndexOf("+"), Feeling.good);
        return counts;
    }

    private boolean isASubject(final TreeMap<Integer, Feeling> map) {
        return map.lastEntry().getKey() >= 0;
    }

    private Feeling getFeeling(final TreeMap<Integer, Feeling> map) {
        return map.lastEntry().getValue();
    }

    private static final String STRING_REPLACE = "\\W";
}
