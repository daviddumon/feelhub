db.feed.find().forEach(function(data) {
    data.opinions.forEach(function(opinion) {
            db.feed.update({"_id":data._id,"opinions.creationDate":opinion.creationDate},{$set:{"opinions.$.text":opinion.value}},false,true);
            db.feed.update({"_id":data._id,"opinions.creationDate":opinion.creationDate},{$unset:{"opinions.$.value":1}},false,true);
    });
});