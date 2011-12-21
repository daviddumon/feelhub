var i = 0;
db.opinion.find().forEach(function (opinion) {
    if (opinion.subjectId = "http://www.kikiyoo.com") {
        print(opinion.subjectId);
        db.opinion.update({"_id":opinion._id}, {$set:{"subjectId":"http://www.steambeat.com"}}, false, true);
    }
});
print(i);

db.opinion.find().forEach(function (opinion) {
    db.opinion.update({"_id":opinion._id}, {$push:{"judgments":{"feeling":opinion.feeling, "subjectId":opinion.subjectId}}}, false, true);

});


db.opinion.update({"_id":opinion._id}, {$unset:{"feeling":1}}, false, true);
    db.opinion.update({"_id":opinion._id}, {$unset:{"subjectId":1}}, false, true);