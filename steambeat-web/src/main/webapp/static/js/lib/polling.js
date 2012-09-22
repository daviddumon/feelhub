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
        $.getJSON(root + "/json/relations?&referenceId=" + referenceId + "&limit=15", function (data) {
            $.each(data, function (index, reference) {
                console.log("relation found " + reference.id);

                var reference_data = {
                    referenceId:reference.id,
                    keyword:"related",
                    classes:"reference_small reference_float reference_zoom",
                    url:"none"
                };

                $("#test_related").append(ich.reference(reference_data));
                RequestIllustration(reference.id);
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