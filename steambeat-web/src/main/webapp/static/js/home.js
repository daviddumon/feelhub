/* Copyright bytedojo 2011 */
$(function () {
    var flow = new Flow();

    loadCounters();
});

function loadCounters() {
    $.getJSON(root + "/statistics?granularity=all&start=0&end=" + new Date().getTime() + "&subjectId=" + steamId, function(data) {
        $.each(data, function(index, stat) {
            $("#counter_good").text(stat.good);
            $("#counter_bad").text(stat.bad);
        })
    });
}

