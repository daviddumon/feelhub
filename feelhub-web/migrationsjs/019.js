var count = 0, deleted = 0;

db.feeling.find().forEach(function (feeling) {
    if (feeling.sentiments.length == 0) {
        db.feeling.remove({_id: feeling._id});
        deleted++;
    }
    count++;
});

print("count: " + count);
print("deleted:" + deleted);