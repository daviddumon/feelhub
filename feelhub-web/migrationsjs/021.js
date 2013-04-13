var count = 0;

db.topic.find().forEach(function(topic) {
    db.topic.update({"_id": topic._id}, {"thumbnail": topic.thumbnails[0].cloudinary});
    count++;
});

print(count);