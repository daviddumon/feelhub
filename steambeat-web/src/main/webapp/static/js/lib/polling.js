function RequestRelations(referenceId) {
    if (referenceId.length != 0) {
        console.log("request relations for " + referenceId);
        $.getJSON(root + "/json/related?&referenceId=" + referenceId + "&limit=12" + "&languageCode=" + languageCode, function (data) {
            $.each(data, function (index, referenceData) {
                console.log("relation found " + referenceData.referenceId);

                var reference_data = {
                    referenceId:referenceData.referenceId,
                    keywordValue:referenceData.keywordValue,
                    classes:"reference_small reference_float reference_zoom",
                    url:"none"
                };

                $("#related").append(ich.reference(reference_data));
                $("#" + referenceData.referenceId + " img").attr("src", referenceData.illustrationLink);
            });
        });
    }
}

function RequestCounters(referenceId) {
    if (referenceId.length != 0) {
        console.log("request counters for " + referenceId);
        $.getJSON(root + "/json/statistics?granularity=all&start=0&end=" + new Date().getTime() + "&referenceId=" + referenceId, function (data) {
            $.each(data, function (index, stat) {
                console.log("load counters success : " + stat);
                $("#counter_good").text(stat.good);
                $("#counter_bad").text(stat.bad);
            });
        });
    }
}