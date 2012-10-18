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
        //console.log("request relations for " + referenceId);
        $.getJSON(root + "/json/related?&referenceId=" + referenceId + "&limit=12" + "&languageCode=" + languageCode, function (data) {
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
        $.getJSON(root + "/json/keyword?keywordValue=" + keywordValue + "&languageCode=" + languageCode, function(data) {

        })
            .success(function(data) {
                //console.log("success");
                //console.log(data);
                clearInterval(referenceIdPolling);
                referenceId = data.referenceId;
                FindInformations();
                flow.pushFake(opinionId, text, feeling);
            });
    }, 500);
};
