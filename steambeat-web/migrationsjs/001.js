db.feed.find().forEach(function (data) {
    db.feed.update({"_id":data._id}, {$rename:{"comments":"opinions"}}, false, false);
});

db.statistics.find().forEach(function (data) {
    db.statistics.update({"_id":data._id}, {$rename:{"goodComments":"goodOpinions"}}, false, false);
    db.statistics.update({"_id":data._id}, {$rename:{"neutralComments":"neutralOpinions"}}, false, false);
    db.statistics.update({"_id":data._id}, {$rename:{"badComments":"badOpinions"}}, false, false);
});

db.feed.find().forEach(function (data) {
    data.opinions.forEach(function (opinion) {
        db.feed.update({"_id":data._id, "opinions.index":opinion.index}, {$unset:{"opinions.$.index":1}}, false, true);
    });
});