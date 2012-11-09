db.topic.find().forEach(function (topic) {
    db.topic.update({"_id":topic._id}, {$unset:{"semanticDescription":1}}, false, true);
    db.topic.update({"_id":topic._id}, {$set:{"scrapedDataExpirationDate":NumberLong('1')}}, false, true);
});