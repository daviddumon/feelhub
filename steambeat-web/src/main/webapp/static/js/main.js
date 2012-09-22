/* Copyright Steambeat 2012 */
$(function () {
    //var flow = new Flow();
    //var form = new Form();
    loadIllustration();
    loadCounters();
    loadRelated();
});

function loadCounters() {
    console.log("load counters");
    $.getJSON(root + "/json/statistics?granularity=all&start=0&end=" + new Date().getTime() + "&topicId=" + referenceId, function (data) {
        $.each(data, function (index, stat) {
            console.log("load counters success : " + stat);
            $("#counter_good").text(stat.good);
            $("#counter_bad").text(stat.bad);
        });
    });
}

function loadRelated() {
    console.log("load relations");
    $("#related_list").empty();
    $.getJSON(root + "/json/relations?&fromId=" + referenceId + "&limit=10", function (data) {
        $.each(data, function (index, subject) {
            console.log("load relations success");
            $("#related_list").append(ich.related(subject));
        });
    });
    //setTimeout(function () {
    //    loadRelated();
    //}, 15000);
}