var FoundException = {};
rs.slaveOk();

var topics = 0, deleted = 0, keeped = 0, count = 0;

var feelings = new Array();
db.feeling.find().forEach(function (feeling) {
    feeling.sentiments.forEach(function (sentiment) {
        feelings.push(sentiment.topicId.hex());
    });
});

var relateds = new Array();
db.related.find().forEach(function (related) {
    relateds.push(related.toId.hex());
});

print(feelings.length);
print(relateds.length);

function in_feeling(id) {
    try {
        for(var i = 0; i < feelings.length; i ++) {
            if(feelings[i] === id.hex()) {
                throw new FoundException();
            }
        }
        return false;
    } catch (e) {
        return true;
    }
}

function in_related(id) {
    try {
        for(var i = 0; i < relateds.length;i++) {
            if(relateds[i] === id.hex()) {
                throw new FoundException();
            }
        }
        return false;
    } catch (e) {
        return true;
    }
}

db.topic.find().forEach(function (topic) {
    print("topic : " + topic._id + " count:" + count);
    if (in_feeling(topic._id) || in_related(topic._id)) {
        keeped++;
    } else {
        db.topic.remove({_id: topic._id});
        deleted++;
        print("deleted");
    }
    topics++;
    count++;
});

print("total : " + topics);
print("delete : " + deleted);
print("keeped : " + keeped);