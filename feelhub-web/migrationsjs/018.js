var count = 0, deleted = 0;
var FoundException = {};

var topics = new Array();
db.topic.find().forEach(function (topic) {
    topics.push(topic._id.hex());
});


function not_in_topics(id) {
    try {
        for (var i = 0; i < topics.length; i++) {
            if (id === topics[i]) {
                throw new FoundException();
            }
        }
        return true;
    } catch (e) {
        return false;
    }
}

db.related.find().forEach(function (related) {
    print("relation : " + related._id + " count: " + count);
    if(not_in_topics(related.fromId.hex()) || not_in_topics(related.toId.hex())) {
        db.related.remove({_id: related._id});
        deleted++;
    }
    count++;
});

print("deleted relations:" + deleted);