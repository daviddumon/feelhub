db.opinion.find().forEach(function (opinion) {
    if (opinion.subjectId == "http://www.kikiyoo.com") {
        db.opinion.update({"_id":opinion._id}, {$set:{"subjectId":"http://www.steambeat.com"}}, false, true);
    }
});

db.opinion.find().forEach(function (opinion) {
    db.opinion.update({"_id":opinion._id}, {$set:{"judgments":[
        {"feeling":opinion.feeling, "subjectId":opinion.subjectId}
    ]}});
});

db.opinion.find().forEach(function (opinion) {
    db.opinion.update({"_id":opinion._id}, {$unset:{"feeling":1}});
    db.opinion.update({"_id":opinion._id}, {$unset:{"subjectId":1}});
});