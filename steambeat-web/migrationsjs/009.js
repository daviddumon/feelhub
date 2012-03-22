db.opinion.copyTo("oldopinion");
db.statistics.drop();
db.association.drop();
db.subject.drop();
db.opinion.drop();
db.system.indexes.dropIndexes();