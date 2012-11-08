db.feeling.copyTo("oldfeeling");
db.statistics.drop();
db.association.drop();
db.subject.drop();
db.feeling.drop();
db.system.indexes.dropIndexes();