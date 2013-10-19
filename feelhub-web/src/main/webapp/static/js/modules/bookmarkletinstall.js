define(["jquery"],

    function ($) {

        var doit;
        var name = "#bookmarkletinstall";
        var step = 1;
        var current_navigator;

        $("body").on("click", "#bookmark-help-button-1", function () {
            $("#step-" + step + "-" + current_navigator).toggle();
            step = 1;
            $("#step-" + step + "-" + current_navigator).toggle();
        });

        $("body").on("click", "#bookmark-help-button-2", function () {
            $("#step-" + step + "-" + current_navigator).toggle();
            step = 2;
            $("#step-" + step + "-" + current_navigator).toggle();
        });

        $("body").on("click", "#bookmark-help-button-3", function () {
            $("#step-" + step + "-" + current_navigator).toggle();
            step = 3;
            $("#step-" + step + "-" + current_navigator).toggle();
        });

        $("body").on("click", "#bookmarklet", function (event) {
            event.preventDefault();
            event.stopImmediatePropagation();
        });

        function init() {
            if (navigator.userAgent.match(/chrome/i)) {
                current_navigator = "chrome";
                $("#step-" + step + "-" + current_navigator).append("<iframe width='600' height='300' src='//www.youtube.com/embed/uJx0BFmzJto?rel=0&autoplay=1&loop=1&playlist=uJx0BFmzJto' frameborder='0' allowfullscreen></iframe>");
            } else if (navigator.userAgent.match(/firefox/i)) {
                current_navigator = "firefox";
                $("#step-" + step + "-" + current_navigator).append("<iframe width='600' height='300' src='//www.youtube.com/embed/u2M1DABVRo4?rel=0&autoplay=1&loop=1&playlist=u2M1DABVRo4' frameborder='0' allowfullscreen></iframe>");
            } else if (navigator.userAgent.match(/safari/i)) {
                current_navigator = "safari";
            } else if (navigator.userAgent.match(/opera/i)) {
                current_navigator = "opera";
            } else {
                current_navigator = "ie";
            }

            $(name).css("left", $(window).width() / 2 - $(name).width() / 2);
            add_responsive_behavior();
            $("#step-" + step + "-" + current_navigator).toggle();
        }

        function add_responsive_behavior() {

            $(window).on("resize", function () {
                clearTimeout(doit);
                doit = setTimeout(function () {
                    end_of_resize();
                }, 200);
            });

            $(window).on("orientationchange", function () {
                clearTimeout(doit);
                doit = setTimeout(function () {
                    end_of_resize();
                }, 200);
            });

            function end_of_resize() {
                $(name).css("top", "82px");
                $(name).css("left", $(window).width() / 2 - $(name).width() / 2);
            }
        }

        return {
            init: init
        }
    });