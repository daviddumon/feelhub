var count = 0;

db.topic.find().forEach(function(topic) {
    if(topic.thumbnails[0].origin.match(/http:\/\/ec2-107-22-105-164/)) {
        db.topic.update({"_id": topic._id}, {$set: {"thumbnails.0.origin": "http://ec2-107-22-105-164.compute-1.amazonaws.com:3000/?url=" + topic.uris[0].value + "&clipRect={%22top%22:0,%22left%22:0,%22width%22:1692,%22height%22:1044}"}}, false, true);
    }
});

print("count:" + count);