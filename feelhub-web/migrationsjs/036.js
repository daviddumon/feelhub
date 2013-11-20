var count = 0;

db.topic.find().forEach(function (topic) {
    print(++count);
    var popularityCount = 0;
    if (typeof topic.boredFeelingCount !== "undefined") {
        popularityCount += topic.boredFeelingCount;
    }
    if (typeof topic.happyFeelingCount !== "undefined") {
        popularityCount += topic.happyFeelingCount;
    }
    if (typeof topic.sadFeelingCount !== "undefined") {
        popularityCount += topic.sadFeelingCount;
    }
    if (typeof topic.viewCount !== "undefined") {
        popularityCount += topic.viewCount;
    }
    topic.popularityCount = NumberInt(popularityCount);
    db.topic.save(topic);
});