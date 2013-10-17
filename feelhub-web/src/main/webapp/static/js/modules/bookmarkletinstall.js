define(["jquery"],

    function ($) {

        var doit;
        var name = "#bookmarkletinstall";
        var step = 1;

        $("body").on("click", ".previous-button", function () {
            $("#step-" + step).toggle();
            step--;
            $("#step-" + step).toggle();
        });

        $("body").on("click", ".next-button", function () {
            $("#step-" + step).toggle();
            step++;
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
                $(name).css("top", $(window).height() / 2 - $(name).height() / 2);
                $(name).css("left", $(window).width() / 2 - $(name).width() / 2);
            }
        }

        return {
            init: init
        }
    });