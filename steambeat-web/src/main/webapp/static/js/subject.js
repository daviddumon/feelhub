/* Copyright Steambeat 2012 */
$(function () {
    var flow = new Flow();
    var form = new Form();
    loadCounters();
    loadRelated();
});

function loadCounters() {

    $.getJSON(root + "/statistics?granularity=all&start=0&end=" + new Date().getTime() + "&subjectId=" + subjectId, function (data) {
        $.each(data, function (index, stat) {
            $("#counter_good").text(stat.good);
            $("#counter_bad").text(stat.bad);
        });
    });
}

function loadRelated() {
    $.getJSON(root + "/related?&fromId=" + subjectId + "&limit=10", function (data) {
        $.each(data, function (index, subject) {
            $(".related_box").append(ich.related(subject));
        });
    });
}