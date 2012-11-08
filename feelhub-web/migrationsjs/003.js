db.feed.find().forEach(function (data) {
    var feedID = data._id;
    db.subject.insert({"_id":data._id, "creationDate":data.creationDate, "__discriminator":"Feed"});
    data.feelings.forEach(function (feeling) {
        db.tempfeeling.insert({"creationDate":feeling.creationDate, "text":feeling.text, "feeling":feeling.feeling, "subjectId":feedID});
    });
});

db.tempfeeling.find().sort({"creationDate":1}).forEach(function (feeling) {
    db.feeling.insert({"creationDate":feeling.creationDate, "text":feeling.text, "feeling":feeling.feeling, "subjectId":feeling.subjectId});
});

db.feed.drop();
db.hotfeelinglink.drop();
db.tempfeeling.drop();

db.statistics.find().forEach(function (data) {
    db.statistics.update({"_id":data._id}, {$set:{"subjectId":data.uri}}, false, true);
    db.statistics.update({"_id":data._id}, {$unset:{"uri":1}}, false, true);
});

db.feeling.find().forEach(function (feeling) {
    if (feeling.text == null) {
        db.feeling.update({"_id":feeling._id}, {$set:{"text":""}}, false, true);
    }
});