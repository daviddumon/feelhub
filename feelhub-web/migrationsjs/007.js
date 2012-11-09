db.topic.find().forEach(function (topic) {
    db.topic.update({"_id":topic._id}, {$set:{"description":""}}, false, true);
    db.topic.update({"_id":topic._id}, {$set:{"shortDescription":"link"}}, false, true);
    db.topic.update({"_id":topic._id}, {$set:{"illustration":""}}, false, true);
    db.topic.update({"_id":topic._id}, {$set:{"scrapedDataExpirationDate":NumberLong('1')}}, false, true);
});