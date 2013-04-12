var begin = new Date();
var FoundException = {};
rs.slaveOk();

var topics = 0, deleted = 0, keeped = 0;

function in_feeling(id) {
    try {
        db.feeling.find().forEach(function (feeling) {
            feeling.sentiments.forEach(function (sentiment) {
                if (sentiment.topicId.hex() === id.hex()) {
                    throw new FoundException();
                }
            });
        });
        return false;
    } catch (e) {
        return true;
    }
}

function in_related(id) {
    try {
        db.related.find().forEach(function (related) {
            if (related.toId.hex() === id.hex()) {
                throw new FoundException();
            }
        });
        return false;
    } catch (e) {
        return true;
    }
}

db.topic.find().forEach(function (topic) {
    print("topic : " + topic._id);
    if (in_feeling(topic._id) || in_related(topic._id)) {
        keeped++;
    } else {
        db.topic.remove({_id: topic._id});
        deleted++;
        print("deleted");
    }
    topics++;
});

print("total : " + topics);
print("delete : " + deleted);
print("keeped : " + keeped);
print("time : " + (new Date() - begin));