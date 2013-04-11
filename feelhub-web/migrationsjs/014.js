db.topic.update( { thumbnailSmall: { $exists: true } }, {$unset: { thumbnailSmall : 1 } }, false, true);
db.topic.update( { thumbnailMedium: { $exists: true } }, {$unset: { thumbnailMedium : 1 } }, false, true);
db.topic.update( { thumbnailLarge: { $exists: true } }, {$unset: { thumbnailLarge : 1 } }, false, true);
db.topic.update( { }, {$set: { thumbnail : "" } }, false, true);