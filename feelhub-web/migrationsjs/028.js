var count = 0;
db.topic.find({names: {},uris: {$size: 1}}).forEach(function (topic) {
    print(topic._id);
    db.topic.update({_id: topic._id}, {$set: {names: {"none": topic.uris[0].value}}});
    count++;
});
print("count:" + count);