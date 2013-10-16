define(["jquery", "modules/canvas"],

    function ($, canvas) {

        var doit;
        var name = "#welcome";

        function init() {
            $(name).css("left", $(window).width() / 2 - $(name).width() / 2);
            add_responsive_behavior();
            canvas.feeling("good", "welcome-feeling-good");
            canvas.feeling("neutral", "welcome-feeling-neutral");
            canvas.feeling("bad", "welcome-feeling-bad");
            canvas.pie("help-pie");
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
                $(name).css("top", $(window).height() / 2 - $(name).height() / 2);
                $(name).css("left", $(window).width() / 2 - $(name).width() / 2);
            }
        }


        return {
            init: init
        }
    });