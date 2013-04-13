var count = 0;

db.topic.find().forEach(function (topic) {
    var thumbnails = new Array();
    thumbnails.push({"origin": topic.illustration, "cloudinary": ""});
    db.topic.update({"_id": topic._id}, {$set: {"thumbnails": thumbnails}});
    count++;
});

db.topic.update({}, {$unset: {thumbnail:1}}, false, true);
db.topic.update({}, {$unset: {illustration:1}}, false, true);

print("count: " + count);