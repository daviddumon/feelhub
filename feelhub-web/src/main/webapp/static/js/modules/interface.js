define(["jquery", "modules/messages", "modules/popup"], function ($, messages, popup) {

    var trigger_height = 0;
    var doit;
    var move_dashboard;

    function init() {
        add_responsive_behavior();
        messages.init();

        $("body").on("submit", "form.sentimentform", function () {
            $.ajax({
                url: root + "/api/feeling/" + $(this).find("[name=feelingid]").val() + "/sentiment/" + $(this).find("[name=index]").val(),
                type: "PUT",
                data: $(this).serialize(),
                success: success,
                error: error
            });
            return false;
        });

        $(window).scroll(function () {
            if (move_dashboard) {
                if ($(window).scrollTop() > trigger_height + 40) {
                    if ($("#dashboard").css("position") != 'fixed') {
                        $("#dashboard").css("position", "fixed");
                        $("#dashboard").css("top", -trigger_height);
                    }
                } else {
                    if ($("#dashboard").css("position") != 'absolute') {
                        $("#dashboard").css("position", "absolute");
                        $("#dashboard").css("top", "42px");
                    }
                }
            }
        });

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
            $("canvas").each(function(index, canvas) {
                $(canvas).trigger("clearanddraw");
            })
            $(".fixed-panel").css("top", $(window).height() / 2 - $(".fixed-panel").height() / 2);
        }
    }

    function success() {
        messages.store_message("neutral", "Goodbye!", 3);
        document.location.reload();
    }

    function error() {
        messages.store_message("bad", "There was a disturbance in the Force", 3);
    }

    return {
        init: init
    };
});