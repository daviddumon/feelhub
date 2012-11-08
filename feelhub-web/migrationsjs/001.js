db.feed.find().forEach(function (data) {
    db.feed.update({"_id":data._id}, {$rename:{"comments":"feelings"}}, false, false);
});

db.statistics.find().forEach(function (data) {
    db.statistics.update({"_id":data._id}, {$rename:{"goodComments":"goodFeelings"}}, false, false);
    db.statistics.update({"_id":data._id}, {$rename:{"neutralComments":"neutralFeelings"}}, false, false);
    db.statistics.update({"_id":data._id}, {$rename:{"badComments":"badFeelings"}}, false, false);
});

db.feed.find().forEach(function (data) {
    data.feelings.forEach(function (feeling) {
        db.feed.update({"_id":data._id, "feelings.index":feeling.index}, {$unset:{"feelings.$.index":1}}, false, true);
    });
});