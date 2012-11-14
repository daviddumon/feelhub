function buildInternalLink(languageCode, value) {
    var result = root + "/topic/";
    if (languageCode != "none") {
        result += languageCode + "/";
    }
    result += value;
    return result;
}

function RequestRelations(topicId) {
    if (topicId.length != 0) {
        var parameters = [];
        var uri = root + "/api/related?";
        parameters.push({"value":"topicId=" + topicId});
        parameters.push({"value":"limit=12"});
        if (typeof userLanguageCode !== 'undefined') {
            parameters.push({"value":"languageCode=" + userLanguageCode});
        } else if (languageCode !== "none") {
            parameters.push({"value":"languageCode=" + languageCode});
        } else {
            parameters.push({"value":"languageCode=en"});
        }
        $.each(parameters, function (index, parameter) {
            uri += parameter.value + "&";
        });
        uri = uri.substr(0, uri.length - 1);
        //console.log(uri);
        $.getJSON(uri, function (data) {
            $.each(data, function (index, topicData) {
                //console.log("relation found " + topicData.topicId);

                var topic_data = {
                    topicId:topicData.topicId,
                    keywordValue:topicData.keywordValue,
                    url:buildInternalLink(topicData.languageCode, topicData.keywordValue),
                    classes:"topic_related"
                };

                $("#related").append(ich.topic(topic_data));
                $("#" + topicData.topicId + " img").attr("src", topicData.illustrationLink);
            });
        });
    }
}

function RequestCounters(topicId) {
    if (topicId.length != 0) {
        $.getJSON(root + "/api/statistics?granularity=all&start=0&end=" + new Date().getTime() + "&topicId=" + topicId, function (data) {
            $.each(data, function (index, stat) {
                $("#counter_good p").text(stat.good);
                $("#counter_bad p").text(stat.bad);
                $("#counter_neutral p").text(stat.neutral);
            });
        });
    }
}

function pollForId(feelingId, text, sentimentValue) {
    var topicIdPolling = setInterval(function () {
        $.getJSON(root + "/api/keyword?keywordValue=" + encodeURIComponent(keywordValue) + "&languageCode=" + languageCode, function (data) {

        })
            .success(function (data) {
                //console.log("success");
                //console.log(data);
                clearInterval(topicIdPolling);
                topicId = data.topicId;
                FindInformations();
                flow.pushFake(feelingId, text, sentimentValue);
            });
    }, 500);
}
