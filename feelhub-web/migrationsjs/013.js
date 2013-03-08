db.feeling.find({'sentiments.sentimentValue' : 'none'}).forEach(
    function(feeling) {
        feeling.sentiments.forEach(function (sentiment) {
            if (sentiment.sentimentValue == "none") sentiment.sentimentValue = "neutral";
        });
        db.feeling.save(feeling);
    }
);