db.feeling.find().forEach(function (feeling) {
    if (feeling.feeling == "neutral") {
        db.feeling.update({"_id":feeling._id}, {$set:{"feeling":"good"}}, false, true);
    }
});