/* Copyright Steambeat 2012 */
$(function () {

    $(window).scroll(function () {
        var trigger = $("#left").height() - $(window).height() + 100;
        if (trigger > 0) {
            if ($(window).scrollTop() <= trigger) {
                $("#left").css("position", "static");
            } else {
                $("#left").css("position", "fixed");
                $("#left").css("top", -trigger + 100);
            }
        } else {
            $("#left").css("position", "fixed");
            $("#left").css("top", "100px");
        }
    });

    var doit;
    $(window).resize(function () {
        clearTimeout(doit);
        doit = setTimeout(function () {
            endOfResize();
        }, 100);
    });

    $(window).on("orientationchange", function () {
        clearTimeout(doit);
        doit = setTimeout(function () {
            endOfResize();
        }, 100);
    });

    function endOfResize() {
        $("#opinions").empty();
        if (typeof flow !== 'undefined') {
            flow.reset();
        }
    }
});
