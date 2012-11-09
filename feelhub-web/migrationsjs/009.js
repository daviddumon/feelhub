db.feeling.copyTo("oldfeeling");
db.statistics.drop();
db.association.drop();
db.topic.drop();
db.feeling.drop();
db.system.indexes.dropIndexes();