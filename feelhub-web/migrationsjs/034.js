var foundrealtopicCount = 0;
var foundhttptopicCount = 0;
var foundgeotopicCount = 0;
var foundftpTopicCount = 0;
var foundworldTopicCount = 0;
var foundAnalyses = 0;
var realtopicCount = db.topic.count({__discriminator: "RealTopic"});
var httptopicCount = db.topic.count({__discriminator: "HttpTopic"});
var geotopicCount = db.topic.count({__discriminator: "GeoTopic"});
var ftpTopicCount = db.topic.count({__discriminator: "FtpTopic"});
var worldTopicCount = db.topic.count({__discriminator: "WorldTopic"});
var analysesCount = db.alchemyanalysis.count();

var httpTopicList = db.topic.find({__discriminator: "HttpTopic"});

httpTopicList.forEach(function (httpTopic) {
    if(httpTopic.typeValue == "Article" || httpTopic.typeValue == "Website" ) {
        var analyses = db.alchemyanalysis.find({topicId: httpTopic.currentId});
        if(analyses.hasNext()) {
            var analyse = analyses.next();
            httpTopic.languageCode = analyse.languageCode;
            foundAnalyses++;
        } else {
            httpTopic.languageCode = "none";
        }
    } else {
        httpTopic.languageCode = "none";
    }
    foundhttptopicCount++;
    db.topic.save(httpTopic);
});

var realTopicList = db.topic.find({__discriminator: "RealTopic"});

realTopicList.forEach(function (realTopic) {
    realTopic.languageCode = "none";
    foundrealtopicCount++;
    db.topic.save(realTopic);
});

var worldTopicList = db.topic.find({__discriminator: "WorldTopic"});

worldTopicList.forEach(function (world) {
    world.languageCode = "none";
    foundworldTopicCount++;
    db.topic.save(world);
});

print("http topics : " + foundhttptopicCount + "/" + httptopicCount);
print("analyses " + foundAnalyses + "/" + analysesCount);
print("real topics : " + foundrealtopicCount + "/" + realtopicCount);
print("geo topics : " + foundgeotopicCount + "/" + geotopicCount);
print("ftp topics : " + foundftpTopicCount + "/" + ftpTopicCount);
print("world topics : " + foundworldTopicCount + "/" + worldTopicCount);