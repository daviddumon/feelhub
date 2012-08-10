/* Copyright Steambeat 2012 */
$(function () {
    var flow = new Flow();
    var form = new Form();
    loadCounters();
    loadRelated();
});

function loadCounters() {
    $.getJSON(root + "/json/statistics?granularity=all&start=0&end=" + new Date().getTime() + "&topicId=" + subjectId, function (data) {
        $.each(data, function (index, stat) {
            $("#counter_good").text(stat.good);
            $("#counter_bad").text(stat.bad);
        });
    });
}

function loadRelated() {
    $("#related_list").empty();
    $.getJSON(root + "/json/related?&fromId=" + subjectId + "&limit=10", function (data) {
        $.each(data, function (index, subject) {
            $("#related_list").append(ich.related(subject));
        });
    });
    setTimeout(function () {
        loadRelated();
    }, 15000);
}