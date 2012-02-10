db.subject.find().forEach(function (subject) {
    db.subject.update({"_id":subject._id}, {$set:{"description":""}}, false, true);
    db.subject.update({"_id":subject._id}, {$set:{"shortDescription":"link"}}, false, true);
    db.subject.update({"_id":subject._id}, {$set:{"illustration":""}}, false, true);
    db.subject.update({"_id":subject._id}, {$set:{"scrapedDataExpirationDate":NumberLong('1')}}, false, true);
});