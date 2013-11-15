db.feeling.find().forEach(function (feeling) {
    if (feeling.feeling == "bored") {
        db.feeling.update({"_id":feeling._id}, {$set:{"feeling":"happy"}}, false, true);
    }
});