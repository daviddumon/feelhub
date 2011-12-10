db.subject.find().forEach(function (subject) {
    db.subject.update({"_id":subject._id}, {$set:{"__discriminator":"WebPage"}}, false, true);
});