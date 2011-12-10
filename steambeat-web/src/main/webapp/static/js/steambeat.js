function loadFlow(flow, skip, limit) {
    $("#loadmore_button").hide();
    $.getJSON(root + "/opinions;" + skip + ";" + limit, function (data) {
      $.each(data, function (index, opinion) {
            flow.drawBox(opinion, "opinion rounded");
        });
    });
    $("#loadmore_button").show();
}

$(function () {
    var skip = 0;
    var limit = 50;
    var flow = new Flow("core.css", "right", "li", ".opinion");
    loadFlow(flow, skip, limit);

    $("#loadmore_button").click(function() {
        skip += limit;
        loadFlow(flow, skip, limit);
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