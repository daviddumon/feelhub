define(["jquery", "modules/messages", "modules/popup"], function ($, messages, popup) {

    var doit;

    function init() {
        add_responsive_behavior();
        messages.init();
        popup.init();
        $(".fixed-panel").css("top", $(window).height() / 2 - $(".fixed-panel").height() / 2);
        $(".fixed-panel").show();
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