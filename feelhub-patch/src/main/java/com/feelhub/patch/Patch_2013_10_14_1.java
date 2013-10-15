package com.feelhub.patch;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.statistics.StatisticsFactory;
import com.feelhub.repositories.*;
import com.mongodb.*;
import org.joda.time.DateTime;

import java.util.*;

public class Patch_2013_10_14_1 extends Patch {

    public Patch_2013_10_14_1(final SessionProvider sessionProvider) {
        super(sessionProvider);
    }

    @Override
    protected boolean withBusinessPatch() {
        return true;
    }

    @Override
    protected void doBusinessPatch() {
        System.out.println("Patch 2013-10-14-1");
        final StatisticsFactory statisticsFactory = new StatisticsFactory();
        final DB db = sessionProvider.get().getDb();
        final DBCollection feelingCollection = db.getCollection("feeling");
        feelingCollection.rename("oldfeeling");
        final DBCollection oldFeelingCollection = db.getCollection("oldfeeling");
        final DBCursor oldFeelings = oldFeelingCollection.find();

        int count = 1;
        while (oldFeelings.hasNext()) {
            final DBObject oldFeeling = oldFeelings.next();
            System.out.println("traitement du feeling " + count + " sur " + oldFeelings.size());
            final BasicDBList sentiments = (BasicDBList) oldFeeling.get("sentiments");
            final Iterator<Object> iterator = sentiments.iterator();
            while (iterator.hasNext()) {
                final DBObject sentiment = (DBObject) iterator.next();
                final Feeling feeling = new FeelingFactory().createFeeling(UUID.fromString(oldFeeling.get("userId").toString()), UUID.fromString(sentiment.get("topicId").toString()));
                feeling.setFeelingValue(FeelingValue.valueOf(sentiment.get("sentimentValue").toString()));
                feeling.setText(oldFeeling.get("text").toString());
                feeling.setLanguageCode(oldFeeling.get("languageCode").toString());
                feeling.setLastModificationDate(new DateTime(oldFeeling.get("lastModificationDate")));
                feeling.setCreationDate(new DateTime(oldFeeling.get("creationDate")));
                Repositories.feelings().add(feeling);
                statisticsFactory.handle(new FeelingCreatedEvent(feeling));
            }
            count++;
        }

        oldFeelingCollection.drop();
    }

    @Override
    public Version version() {
        return new Version(1);
    }
}
