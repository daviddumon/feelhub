db.relation.ensureIndex({"fromId":1});
db.relation.ensureIndex({"fromId":1,"toId":1,"weight":-1});
db.opinion.ensureIndex({"creationDate":-1});
db.association.ensureIndex({"language":1,"identifier":1});