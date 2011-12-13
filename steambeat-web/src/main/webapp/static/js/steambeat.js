$(function () {
    var button = $("<div id='loadmore_button' class='rounded'>load more</div>");
    $("#main").append(button);
    var flow = new Flow("core.css", "opinions", ".opinion", "loadmore_button");

    $(button).click(function() {
        flow.next();
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