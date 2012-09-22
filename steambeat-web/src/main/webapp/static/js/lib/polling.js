function RequestIllustration(referenceId) {
    if (referenceId.length != 0) {
        console.log("request illustration for " + referenceId);
        $.getJSON(root + "/json/illustrations?referenceId=" + referenceId, function (data) {
            $.each(data, function (index, illustration) {
                console.log("illustration received : " + illustration.referenceId + " - " + illustration.link);
                $("#" + illustration.referenceId).attr("src", illustration.link);
            });
        });
    }
}

function RequestRelations(referenceId) {
    if (referenceId.length != 0) {
        console.log("request relations for " + referenceId);

    }
    //$("#related_list").empty();
    //$.getJSON(root + "/json/relations?&fromId=" + referenceId + "&limit=10", function (data) {
    //    $.each(data, function (index, subject) {
    //        console.log("load relations success");
    //        $("#related_list").append(ich.related(subject));
    //    });
    //});
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