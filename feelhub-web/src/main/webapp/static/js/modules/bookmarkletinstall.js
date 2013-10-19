define(["jquery"],

    function ($) {

        var doit;
        var name = "#bookmarkletinstall";
        var step = 1;
        var current_navigator;

        $("body").on("click", "#bookmark-help-button-1", function () {
            $("#step-" + step + "." + current_navigator).toggle();
            step = 1;
            addGoodPlayer();
            $("#step-" + step + "." + current_navigator).toggle();
        });

        $("body").on("click", "#bookmark-help-button-2", function () {
            $("#step-" + step + "." + current_navigator).toggle();
            if (step == 1) {
                $("#step-" + step + "." + current_navigator + " iframe").remove();
            }
            step = 2;
            $("#step-" + step + "." + current_navigator).toggle();
        });

        $("body").on("click", "#bookmark-help-button-3", function () {
            $("#step-" + step + "." + current_navigator).toggle();
            if (step == 1) {
                $("#step-" + step + "." + current_navigator + " iframe").remove();
            }
            step = 3;
            $("#step-" + step + "." + current_navigator).toggle();
        });

        $("body").on("click", "#bookmarklet", function (event) {
            event.preventDefault();
            event.stopImmediatePropagation();
        });

        function init() {
            if (navigator.userAgent.match(/chrome/i)) {
                current_navigator = "chrome";
            } else if (navigator.userAgent.match(/firefox/i)) {
                current_navigator = "firefox";
            } else if (navigator.userAgent.match(/safari/i)) {
                current_navigator = "safari";
            } else if (navigator.userAgent.match(/opera/i)) {
                current_navigator = "opera";
            } else {
                current_navigator = "ie";
            }
            addGoodPlayer();
            $(name).css("left", $(window).width() / 2 - $(name).width() / 2);
            add_responsive_behavior();

            //if ($(window).width() > 912) {
                $("#step-" + step + "." + current_navigator).toggle();
                //$.post(root + "/api/user/bookmarkletshow", "bookmarkletShow=false");
            //} else {
            //$("#overlay").hide();
            //    $(name).hide();
            //}
        }

        function addGoodPlayer() {
            if (navigator.userAgent.match(/chrome/i)) {
                $("#step-" + step + "." + current_navigator).append("<iframe width='600' height='330' src='//www.youtube.com/embed/uJx0BFmzJto?rel=0&autoplay=1&loop=1&playlist=uJx0BFmzJto' frameborder='0' allowfullscreen></iframe>");
            } else if (navigator.userAgent.match(/firefox/i)) {
                $("#step-" + step + "." + current_navigator).append("<iframe width='600' height='295' src='//www.youtube.com/embed/u2M1DABVRo4?rel=0&autoplay=1&loop=1&playlist=u2M1DABVRo4' frameborder='0' allowfullscreen></iframe>");
            } else if (navigator.userAgent.match(/safari/i)) {
                $("#step-" + step + "." + current_navigator).append("<iframe width='600' height='330' src='//www.youtube.com/embed/CwlzZZHWh14?rel=0&autoplay=1&loop=1&playlist=CwlzZZHWh14' frameborder='0' allowfullscreen></iframe>");
            } else if (navigator.userAgent.match(/opera/i)) {
                $("#step-" + step + "." + current_navigator).append("<iframe width='600' height='330' src='//www.youtube.com/embed/RCM-7ct_Oc4?rel=0&autoplay=1&loop=1&playlist=RCM-7ct_Oc4' frameborder='0' allowfullscreen></iframe>");
            } else {
                $("#step-" + step + "." + current_navigator).append("<iframe width='600' height='330' src='//www.youtube.com/embed/031qerbZ0vU?rel=0&autoplay=1&loop=1&playlist=031qerbZ0vU' frameborder='0' allowfullscreen></iframe>");
            }
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
                if ($(window).width() < 912) {
                    $(name).hide();
                }
                $(name).css("top", "82px");
                $(name).css("left", $(window).width() / 2 - $(name).width() / 2);
            }
        }

        return {
            init: init
        }
    });