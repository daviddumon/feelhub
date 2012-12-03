define(["jquery"], function ($) {

    function buildInternalLink(type, languageCode, value) {
        var result = root + "/" + type + "/";
        if (languageCode != "none") {
            result += languageCode + "/";
        }
        result += encodeURIComponent(value);
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
                $.each(data, function (index, keywordData) {
                    //console.log("relation found " + keywordData.topicId);

                    var keyword_data = {
                        topicId:keywordData.topicId,
                        keywordValue:keywordData.keywordValue,
                        url:buildInternalLink(keywordData.typeValue, keywordData.languageCode, keywordData.keywordValue),
                        classes:"keyword_related"
                    };

                    $("#related").append(ich.keyword(keyword_data));
                    $("#" + keywordData.topicId + " img").attr("src", keywordData.illustrationLink);
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

            var parameters = [];
            var uri = root + "/api/" + typeValue + "?";
            parameters.push({"value":"keywordValue=" + encodeURIComponent(keywordValue)});
            if (languageCode !== "none") {
                parameters.push({"value":"languageCode=" + languageCode});
            }
            $.each(parameters, function (index, parameter) {
                uri += parameter.value + "&";
            });
            uri = uri.substr(0, uri.length - 1);

            $.getJSON(uri, function (data) {

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

    return {
        buildInternalLink:buildInternalLink,
        RequestRelations:RequestRelations,
        RequestCounters:RequestCounters,
        pollForId:pollForId
    }
});
