/* Copyright Steambeat 2012 */
$(function () {
    var flow = new Flow();
    var form = new Form();

    loadCounters();
});

function loadCounters() {
    $.getJSON(root + "/json/statistics?granularity=all&start=0&end=" + new Date().getTime() + "&topicId=" + steamId, function(data) {
        $.each(data, function(index, stat) {
            $("#counter_good").text(stat.good);
            $("#counter_bad").text(stat.bad);
        })
    });
}

