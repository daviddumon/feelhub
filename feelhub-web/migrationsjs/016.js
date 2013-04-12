rs.slaveOk();

var topics = 0, deleted = 0, keeped = 0;

function in_feeling(id) {
    var result = false;
    db.feeling.find().forEach(function (feeling) {
        feeling.sentiments.forEach(function (sentiment) {
            if (sentiment.topicId.hex() === id.hex()) {
                result = true;
            }
        });
    });
    return result;
}

function in_related(id) {
    var result = false;
    db.related.find().forEach(function (related) {
        if(related.toId.hex() === id.hex()) {
            result = true;
        }
    });
    return result;
}

db.topic.find().forEach(function (topic) {
    if (in_feeling(topic._id) || in_related(topic._id)) {
        keeped++;
    } else {
        db.topic.remove({_id: topic._id});
        deleted++;
    }
    topics++;
    print("topic : " + topic._id);
});

print("total : " + topics);
print("delete : " + deleted);
print("keeped : " + keeped);