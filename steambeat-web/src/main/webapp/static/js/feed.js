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

    var form = $("#post_opinion");

    //$("#opinion_submit").click(postWith());
    $("#submit_good").click(postWith("good"));
    $("#submit_bad").click(postWith("bad"));
    $("#submit_neutral").click(postWith("neutral"));

    function postWith(feeling) {
        return function(e) {
            e.preventDefault();
            e.stopImmediatePropagation();
            doPost(feeling);
            return false;
        }
    }

    function doPost(feeling) {
        //var feeling = $("#opiniontype").html();
        var values = form.serializeArray();
        values.push({name : "feeling", value : feeling});
        $.post(form.attr("action"), values, function(data, textStatus, jqXHR) {
            location.href = jqXHR.getResponseHeader("Location");
        });
    }

    var doReload;
    $(window).resize(function() {
        clearTimeout(doReload);
        doReload = setTimeout(function(){
            window.location.reload();
        }, 200);
    });

    //setOpinionType(Math.round(Math.random() * 2));
    //
    //function setOpinionType(index) {
    //    switch(index) {
    //        case 0:
    //            $("#opiniontype").html("neutral");
    //            $("#newopinion").css("background-color","#5BA3D1");
    //            break;
    //        case 1:
    //            $("#opiniontype").html("bad");
    //            $("#newopinion").css("background-color","#FF8D66");
    //            break;
    //        case 2:
    //            $("#opiniontype").html("good");
    //            $("#newopinion").css("background-color","#A8F261");
    //            break;
    //    }
    //}
});

function runTimeLine(uri) {
    timeline.init("day", uri, $("#timeline_display").width(), 100, 5, 1, 15, "timeline");
    timeline.loadInitialGraphs(0,0);

//    timeline.backButton.click(function(event) {
//        timeline.back();
//    });
//
//    timeline.forwardButton.click(function(event) {
//        timeline.forward();
//    });
//
//    timeline.upButton.click(function(event) {
//        timeline.up();
//    });
//
//    timeline.downButton.click(function(event) {
//        timeline.down();
//    });
}