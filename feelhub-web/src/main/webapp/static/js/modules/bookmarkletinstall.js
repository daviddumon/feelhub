define(["jquery"],

    function ($) {

        var doit;
        var name = "#bookmarkletinstall";
        var step = 1;

        $("body").on("click", "#bookmark-help-button-1", function () {
            $("#step-" + step).toggle();
            step = 1;
            $("#step-" + step).toggle();
        });

        $("body").on("click", "#bookmark-help-button-2", function () {
            $("#step-" + step).toggle();
            step = 2;
            $("#step-" + step).toggle();
        });

        $("body").on("click", "#bookmark-help-button-3", function () {
            $("#step-" + step).toggle();
            step = 3;
            $("#step-" + step).toggle();
        });

        function init() {
            $(name).css("left", $(window).width() / 2 - $(name).width() / 2);
            add_responsive_behavior();
            $("#step-" + step).toggle();
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