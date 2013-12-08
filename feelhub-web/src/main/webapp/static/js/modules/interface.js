define(["jquery", "modules/messages", "modules/popup", "modules/filters"],

    function ($, messages, popup, filters) {

        var doit;

        function init() {
            add_responsive_behavior();
            messages.init();
            popup.init();
            filters.init();

            $(".fixed-panel").css("top", $(window).height() / 2 - $(".fixed-panel").height() / 2);
            $(".fixed-panel").show();

            $("body").on("click", "#user", function () {
                $(this).toggleClass("show");
                return false;
            });

            if (authentificated == false) {
                var welcomePage = sessionStorage.getItem("welcomePage");
                //if (welcomePage == null) {
                    $("body").trigger("show-welcome");
                    //sessionStorage.setItem("welcomePage", true);
                //}
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
                $("canvas").each(function (index, canvas) {
                    $(canvas).trigger("clearanddraw");
                })
                $(".fixed-panel").css("top", $(window).height() / 2 - $(".fixed-panel").height() / 2);
            }
        }

        return {
            init: init
        };
    });