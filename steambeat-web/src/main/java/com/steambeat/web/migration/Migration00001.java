package com.steambeat.web.migration;

import com.google.common.collect.Lists;
import com.mongodb.*;
import com.steambeat.application.*;
import com.steambeat.application.dto.JudgmentDTO;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.*;
import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.SessionProvider;

import java.util.List;

public class Migration00001 extends Migration {

    public Migration00001(final SessionProvider provider) {
        super(provider, 1);
    }

    @Override
    protected void doRun() {
        final AssociationService associationService = new AssociationService(new UriPathResolver());
        final SubjectService subjectService = new SubjectService(new WebPageFactory());
        final OpinionService opinionService = new OpinionService();
        final DB db = provider.get().getDb();
        final DBCollection oldopinions = db.getCollection("oldopinion");
        final DBCursor cursor = oldopinions.find();
        int count = 0;
        while (cursor.hasNext()) {
            final DBObject opinion = cursor.next();
            System.out.println("Found old opinion to migrate " + opinion.get("_id"));
            final BasicDBList judgments = (BasicDBList) opinion.get("judgments");
            final BasicDBObject judgment = (BasicDBObject) judgments.get(0);
            final Association association = associationService.createAssociationsFor(new Uri(judgment.get("subjectId").toString()));
            final WebPage webPage = subjectService.addWebPage(association);
            String text = opinion.get("text").toString();
            final JudgmentDTO dto = new JudgmentDTO(webPage, Feeling.valueOf(judgment.get("feeling").toString()));
            List<JudgmentDTO> dtos = Lists.newArrayList();
            dtos.add(dto);
            opinionService.addOpinion(text, dtos);
            System.out.println("count:" + count++);
        }
        cursor.close();
        oldopinions.drop();
    }
}
