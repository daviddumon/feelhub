$(function () {
    var flow = new Flow("core.css", "right", "li", ".opinion");
    $.getJSON(root + "/opinions;0;100", function (data) {
        $.each(data, function (index, opinion) {
            flow.drawBox(opinion, "opinion rounded");
        });
    });

    //var doReload;
    //$(window).resize(function() {
    //    clearTimeout(doReload);
    //    doReload = setTimeout(function(){
    //        window.location.reload();
    //    }, 200);
    //});

    //var width = $("#timeline_display") != null ? $("#timeline_display").width() : 0;
    //timeline.init("day", root + "/stats:", width, 100, 5, 2, 15, "timeline");
    //timeline.loadInitialGraphs(0,0);
});