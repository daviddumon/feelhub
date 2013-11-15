db.topic.update({}, {$rename: {"badFeelingCount": "sadFeelingCount"}}, false, true);
db.topic.update({}, {$rename: {"goodFeelingCount": "happyFeelingCount"}}, false, true);
db.topic.update({}, {$rename: {"neutralFeelingCount": "boredFeelingCount"}}, false, true);