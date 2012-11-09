db.feeling.find().forEach(function (feeling) {
    if (feeling.topicId == "http://www.kikiyoo.com") {
        db.feeling.update({"_id":feeling._id}, {$set:{"topicId":"http://www.feelhub.com"}}, false, true);
    }
});

db.feeling.find().forEach(function (feeling) {
    db.feeling.update({"_id":feeling._id}, {$set:{"judgments":[
        {"feeling":feeling.feeling, "topicId":feeling.topicId}
    ]}});
});

db.feeling.find().forEach(function (feeling) {
    db.feeling.update({"_id":feeling._id}, {$unset:{"feeling":1}});
    db.feeling.update({"_id":feeling._id}, {$unset:{"topicId":1}});
});