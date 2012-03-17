db.subject.find().forEach(function (subject) {
    db.subject.update({"_id":subject._id}, {$unset:{"semanticDescription":1}}, false, true);
    db.subject.update({"_id":subject._id}, {$set:{"scrapedDataExpirationDate":NumberLong('1')}}, false, true);
});