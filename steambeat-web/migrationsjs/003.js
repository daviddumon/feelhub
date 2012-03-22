db.feed.find().forEach(function (data) {
    var feedID = data._id;
    db.subject.insert({"_id":data._id, "creationDate":data.creationDate, "__discriminator":"Feed"});
    data.opinions.forEach(function (opinion) {
        db.tempopinion.insert({"creationDate":opinion.creationDate, "text":opinion.text, "feeling":opinion.feeling, "subjectId":feedID});
    });
});

db.tempopinion.find().sort({"creationDate":1}).forEach(function (opinion) {
    db.opinion.insert({"creationDate":opinion.creationDate, "text":opinion.text, "feeling":opinion.feeling, "subjectId":opinion.subjectId});
});

db.feed.drop();
db.hotopinionlink.drop();
db.tempopinion.drop();

db.statistics.find().forEach(function (data) {
    db.statistics.update({"_id":data._id}, {$set:{"subjectId":data.uri}}, false, true);
    db.statistics.update({"_id":data._id}, {$unset:{"uri":1}}, false, true);
});

db.opinion.find().forEach(function (opinion) {
    if (opinion.text == null) {
        db.opinion.update({"_id":opinion._id}, {$set:{"text":""}}, false, true);
    }
});