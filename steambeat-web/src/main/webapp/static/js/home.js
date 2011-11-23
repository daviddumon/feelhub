/* Copyright bytedojo 2011 */
$(function() {

    var flow = new Flow("main.css","webpage","li",".opinion");
    $.getJSON(root + "/opinions", function(data) {
        data.reverse();
        $.each(data, function(index, opinion) {
            var id = flow.drawBoxLink(opinion.text, "opinion " + "opinion_" + opinion.feeling, root + "/webpages/" + opinion.subjectId);
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
    timeline.init("day", root + "/stats:", width, 60, 5, 0, 15, "timeline");
    timeline.loadInitialGraphs(0,0);
});