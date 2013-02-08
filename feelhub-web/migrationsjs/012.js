var noid_count = 0;
db.feeling.find().forEach(function(feeling) {
    var new_sentiments = [];
    feeling.sentiments.forEach(function (sentiment) {
        if(sentiment.topicId === "" || sentiment.topicId == null || typeof sentiment.topicId == 'undefined') {
            printjson(feeling);
            noid_count++;
        } else {
            new_sentiments.push({"sentimentValue": sentiment.sentimentValue, "topicId": sentiment.topicId, "creationDate": sentiment.creationDate});
        }
    });
    db.feeling.update({"_id":feeling._id}, {$unset:{"text":1}}, false, false);
    db.feeling.update({"_id":feeling._id}, {$set:{"sentiments": new_sentiments}}, false, false);
    db.feeling.update({"_id": feeling._id}, {$rename: {"rawText": "text"}});

});
print(noid_count);
