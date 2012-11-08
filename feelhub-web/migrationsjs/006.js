db.feeling.find().forEach(function (feeling) {
    if (feeling.subjectId == "http://www.kikiyoo.com") {
        db.feeling.update({"_id":feeling._id}, {$set:{"subjectId":"http://www.feelhub.com"}}, false, true);
    }
});

db.feeling.find().forEach(function (feeling) {
    db.feeling.update({"_id":feeling._id}, {$set:{"judgments":[
        {"feeling":feeling.feeling, "subjectId":feeling.subjectId}
    ]}});
});

db.feeling.find().forEach(function (feeling) {
    db.feeling.update({"_id":feeling._id}, {$unset:{"feeling":1}});
    db.feeling.update({"_id":feeling._id}, {$unset:{"subjectId":1}});
});