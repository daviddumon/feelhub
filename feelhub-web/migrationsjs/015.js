db.topic.update( { thumbnail: { $exists: true } }, {$unset: { thumbnail : 1 } }, false, true);
db.topic.update( { }, {$set: { thumbnail : "" } }, false, true);