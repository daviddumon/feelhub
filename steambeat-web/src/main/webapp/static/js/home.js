/* Copyright bytedojo 2011 */
$(function() {

    var flow = new Flow("main.css","feed","li",".opinion");
    $.getJSON(root + "/lastopinions", function(data) {
        data.reverse();
        $.each(data, function(index, opinion) {
            var id = flow.drawBoxLink(opinion.text, "opinion " + "opinion_" + opinion.feeling, root + "/feeds/" + opinion.uri);
            flow.compute(id);
        });
    });

    var doReload;
    $(window).resize(function() {
        clearTimeout(doReload);
        doReload = setTimeout(function(){
            window.location.reload();
        }, 200);
    });

    var width = $("#timeline_display") != null ? $("#timeline_display").width() : 0;
    timeline.init("day", root + "/stats:", width, 100, 5, 1, 15, "timeline");
    timeline.loadInitialGraphs(0,0);
});