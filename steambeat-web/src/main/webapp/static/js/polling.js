function buildInternalLink(languageCode, value) {
    var result = root + "/topic/";
    if (languageCode != "none") {
        result += languageCode + "/";
    }
    result += value;
    return result;
}

function RequestRelations(referenceId) {
    if (referenceId.length != 0) {
        var parameters = [];
        var uri = root + "/json/related?";
        parameters.push({"value":"referenceId=" + referenceId});
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
            $.each(data, function (index, referenceData) {
                //console.log("relation found " + referenceData.referenceId);

                var reference_data = {
                    referenceId:referenceData.referenceId,
                    keywordValue:referenceData.keywordValue,
                    url:buildInternalLink(referenceData.languageCode, referenceData.keywordValue),
                    classes:"reference_small reference_float reference_zoom"
                };

                $("#related").append(ich.reference(reference_data));
                $("#" + referenceData.referenceId + " img").attr("src", referenceData.illustrationLink);
                $("#" + referenceData.referenceId).hover(function () {
                    $(this).find("span").fadeIn(150);
                }, function () {
                    $(this).find("span").hide();
                });
            });
        });
    }
}

function RequestCounters(referenceId) {
    if (referenceId.length != 0) {
        $.getJSON(root + "/json/statistics?granularity=all&start=0&end=" + new Date().getTime() + "&referenceId=" + referenceId, function (data) {
            $.each(data, function (index, stat) {
                $("#counter_good p").text(stat.good);
                $("#counter_bad p").text(stat.bad);
                $("#counter_neutral p").text(stat.neutral);
            });
        });
    }
}

function pollForId(opinionId, text, feeling) {
    var referenceIdPolling = setInterval(function () {
        $.getJSON(root + "/json/keyword?keywordValue=" + keywordValue + "&languageCode=" + languageCode, function (data) {

        })
            .success(function (data) {
                //console.log("success");
                //console.log(data);
                clearInterval(referenceIdPolling);
                referenceId = data.referenceId;
                FindInformations();
                flow.pushFake(opinionId, text, feeling);
            });
    }, 500);
}
;
