/* Copyright bytedojo 2011 */
$(function() {
    $("#help").hide();
    $("#help").css("height","0px");

    $("#helpbutton").click(function(){
        if($("#help").is(":hidden")) {
            $("#helpbutton").animate({top: '+=10'}, 200, function() {
                $("#helpbutton").animate({top: '-=10'}, 100, function(){
                    $("#help").css("height","250px");
                    $("#help").slideDown("slow", function(){});
                });
            });
        } else {
            $("#helpbutton").animate({top: '-=10'}, 200, function() {
                $("#helpbutton").animate({top: '+=10'}, 100, function(){
                    $("#help").slideUp("slow", function() {
                        $("#help").css("height","0px");
                    });
                });
            });
        }
    });

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

        //timeline.backButton.click(function(event) {
        //timeline.back();
    //});
    //
    //timeline.forwardButton.click(function(event) {
    //    timeline.forward();
    //});
    //
    //timeline.upButton.click(function(event) {
    //    timeline.up();
    //});
    //
    //timeline.downButton.click(function(event) {
    //    timeline.down();
    //});
});