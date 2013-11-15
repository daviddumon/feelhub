db.statistics.update({}, {$rename: {"good": "happy"}}, false, true);
db.statistics.update({}, {$rename: {"bad": "sad"}}, false, true);
db.statistics.update({}, {$rename: {"neutral": "bored"}}, false, true);