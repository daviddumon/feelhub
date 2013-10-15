db.topic.ensureIndex({"hasFeelings": 1, "lastModificationDate": -1});
db.feeling.ensureIndex({"text": 1, "topicId": 1, "creationDate": -1});