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
            if(step == 3) {
                playStep3Video();
            }
        });

        function playStep3Video() {

        }

        function init() {
            $(name).css("left", $(window).width() / 2 - $(name).width() / 2);
            add_responsive_behavior();
            $("#step-" + step).toggle();
            $("#video-psZ8bIC1Lpc").trigger("playVideo");
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