db.topic.find().forEach(function (topic) {
    db.topic.update({"_id":topic._id}, {$set:{"__discriminator":"WebPage"}}, false, true);
});