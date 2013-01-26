/* Copyright Feelhub 2012 */
define(['jquery'], function ($) {

    var offset, max_offset = 0;
    var nav_width = 60;
    var li_width = 300;
    var comfort_width = 20;
    var doit;

    function init() {
        compute_correct_values();
        add_responsive_behavior();
        add_nav_behavior();
    }

    function add_responsive_behavior() {

        $(window).resize(function () {
            clearTimeout(doit);
            doit = setTimeout(function () {
                compute_correct_values();
            }, 100);
        });

        $(window).on("orientationchange", function () {
            clearTimeout(doit);
            doit = setTimeout(function () {
                compute_correct_values();
            }, 100);
        });
    }

    function compute_correct_values() {
        var current_dashboard_width = $("#dashboard li").length * li_width;
        var current_window_width = $(window).width();
        var new_carousel_width = $("#carousel").width();
        var new_nav_state = "inline-block";

        if (current_window_width > current_dashboard_width + comfort_width) {
            new_nav_state = "none";
            new_carousel_width = current_dashboard_width;
        } else {
            var usable_width = Math.floor((current_window_width - nav_width - comfort_width) / li_width) * li_width;
            if (usable_width >= li_width) {
                new_carousel_width = usable_width;
            } else {
                new_carousel_width = li_width;
                new_nav_state = "none";
            }
        }

        $("#carousel").width(new_carousel_width);
        $("#topic-name").width(new_carousel_width);
        $(".nav").css("display", new_nav_state);

        max_offset = new_carousel_width - current_dashboard_width;
        offset = 0;
        $("#dashboard").animate({"marginLeft": offset}, 200);
    }

    function add_nav_behavior() {
        $("#carousel-prev").click(function () {
            if (offset < 0) {
                offset += li_width;
                $("#dashboard").animate({"marginLeft": offset}, 200);
            }
        });

        $("#carousel-next").click(function () {
            if (offset > max_offset) {
                offset -= li_width;
                $("#dashboard").animate({"marginLeft": offset}, 200);
            }
        });
    }

    return {
        init: init,
        compute: compute_correct_values
    }
})