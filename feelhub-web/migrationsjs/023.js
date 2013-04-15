var count = 0;

db.topic.find({"thumbnail":"","__discriminator":"HttpTopic","typeValue":"Image"}).forEach(function(topic) {
    db.topic.update({"_id": topic._id}, {$set: {"thumbnails.0.origin": topic.uris[0].value}});
    count++;
});

print("count:" + count);