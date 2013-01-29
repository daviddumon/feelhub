db.createCollection("related");
db.createCollection("media");

db.relation.find().forEach(function(data) {
    if(data.__discriminator == "Related") {
        db.related.insert({"_id":data._id,"fromId":data.fromId,"toId":data.toId,"weight":data.weight,"creationDate":data.creationDate,"lastModificationDate":data.lastModificationDate});
    } else {
        db.media.insert({"_id":data._id,"fromId":data.fromId,"toId":data.toId,"creationDate":data.creationDate,"lastModificationDate":data.lastModificationDate});
    }
});

db.relation.drop();