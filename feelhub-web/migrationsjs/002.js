db.feed.find().forEach(function (data) {
    data.feelings.forEach(function (feeling) {
        db.feed.update({"_id":data._id, "feelings.creationDate":feeling.creationDate}, {$set:{"feelings.$.text":feeling.value}}, false, true);
        db.feed.update({"_id":data._id, "feelings.creationDate":feeling.creationDate}, {$unset:{"feelings.$.value":1}}, false, true);
    });
});