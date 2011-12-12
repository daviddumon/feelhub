db.opinion.find().forEach(function(opinion){
    if(opinion.feeling == "neutral") {
        db.opinion.update({"_id":opinion._id},{$set:{"feeling":"good"}},false,true);
    }
});