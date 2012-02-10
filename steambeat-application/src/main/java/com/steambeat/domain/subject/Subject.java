package com.steambeat.domain.subject;

import com.steambeat.domain.*;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.scrapers.Scraper;
import org.joda.time.DateTime;

public abstract class Subject extends BaseEntity {

    protected Subject() {

    }

    protected Subject(final String id) {
        this.creationDate = new DateTime();
        this.id = id;
    }

    public Judgment createJudgment(final Feeling feeling) {
        final Judgment judgment = new Judgment(this, feeling);
        DomainEventBus.INSTANCE.spread(new JudgmentPostedEvent(judgment));
        return judgment;
    }

    //todo
    public Opinion createOpinion(final String text, final Feeling feeling) {
        final Opinion opinion = new Opinion(text);
        opinion.addJudgment(this, feeling);
        DomainEventBus.INSTANCE.spread(new OpinionPostedEvent(opinion));
        return opinion;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public String getId() {
        return id;
    }

    public void update(final Scraper scraper) {
        description = scraper.getDescription();
        shortDescription = scraper.getShortDescription();
        illustration = scraper.getIllustration();
        scrapedDataExpirationDate = new DateTime().plusDays(1);
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getIllustration() {
        return illustration;
    }

    public DateTime getScrapedDataExpirationDate() {
        return scrapedDataExpirationDate;
    }

    protected String shortDescription;
    protected String description;
    protected String illustration;
    private DateTime creationDate;
    protected DateTime scrapedDataExpirationDate;
    private String id;
}
