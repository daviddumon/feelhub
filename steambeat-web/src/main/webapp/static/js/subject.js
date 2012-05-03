/* Copyright bytedojo 2011 */
$(function () {
    var flow = new Flow();

    var lock = false;
    if ($(window).width() <= 720) {
        var lock = true;
    }

    $(window).scroll(function () {
        if (!lock) {
            if ($(window).scrollTop() <= 0) {
                $("#fixed_layer").css("top", "0px");
            } else if ($(window).scrollTop() < 300) {
                $("#fixed_layer").css("top", -$(window).scrollTop());
            } else {
                $("#fixed_layer").css("top", "-300px");
            }
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
        if ($(window).width() <= 720) {
            lock = true;
        } else {
            lock = false;
        }
        $("#opinions").empty();
        $("#opinions").css("height", "0px");
        flow.reset();
    }

    loadCounters();
    loadRelated();
});

function loadCounters() {

    $.getJSON(root + "/statistics?granularity=all&start=0&end=" + new Date().getTime() + "&subjectId=" + subjectId, function(data) {
        $.each(data, function (index, stat) {
            $("#counter_good").text(stat.good);
            $("#counter_bad").text(stat.bad);
        });
    });
}

function loadRelated() {
    $.getJSON(root + "/related?&fromId=" + subjectId +"&limit=10", function (data) {
        $.each(data, function (index, subject) {
            $("#related_list").append(ich.related(subject));
        });
    });
}