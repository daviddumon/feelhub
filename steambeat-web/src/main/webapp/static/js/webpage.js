/* Copyright bytedojo 2011 */
$(function() {

    var form = $("#post_opinion");

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
});

function runTimeLine(uri) {
    timeline.init("day", uri, $("#timeline_display").width(), 100, 5, 1, 15, "timeline");
    timeline.loadInitialGraphs(0,0);
}