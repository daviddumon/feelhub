package com.steambeat.domain.opinion;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;
import org.joda.time.DateTime;

import java.util.Date;

public class Opinion extends BaseEntity {

    protected Opinion() {
    }

    public Opinion(final String text, final Feeling feeling, final Subject subject) {
        this.text = text;
        this.feeling = feeling;
        this.subjectId = subject.getId();
        creationDate = new DateTime();
    }

    //public static String parse(final WebPage webPage, final String value, final Context context) {
    //    if (value != null) {
    //        final String[] tokens = value.split(" ");
    //        final StringBuilder stringBuilder = new StringBuilder();
    //        for (String token : tokens) {
    //            if (token.matches("@[0-9]*")) {
    //                token = transform(webPage, token, context);
    //            }
    //            stringBuilder.append(token + " ");
    //        }
    //        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    //        return stringBuilder.toString();
    //    }
    //    return null;
    //}
    //
    //private static String transform(final WebPage webPage, final String token, final Context context) {
    //    final int opinionIndex = Integer.valueOf(token.substring(1, token.length()));
    //    final String link = "<a href=\""
    //            + new ReferenceBuilder(context).buildUri("/webpages/" + webPage.getId() + "/opinions/" + opinionIndex)
    //            + "\">" + token + "</a>";
    //    return link;
    //}

    public String getText() {
        return text;
    }

    public Feeling getFeeling() {
        return feeling;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public Date getCreationDateAsDate() {
        return creationDate.toDate();
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Subject getSubject() {
        return Repositories.webPages().get(subjectId);
    }

    public Object getSubjectId() {
        return subjectId;
    }

    @Override
    public String getId() {
        return id;
    }

    private String text;
    private Feeling feeling;
    private Object subjectId;
    private DateTime creationDate;
    private String id;
}
