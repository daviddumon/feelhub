db.feeling.find().forEach(function (feeling) {
    var oldvalue = feeling.feelingValue;
    if (oldvalue == "good") {
        feeling.feelingValue = "happy";
    } else if (oldvalue == "bad") {
        feeling.feelingValue = "sad";
    } else {
        feeling.feelingValue = "bored";
    }
    db.feeling.save(feeling);
});