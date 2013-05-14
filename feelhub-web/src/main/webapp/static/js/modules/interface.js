define(["jquery", "modules/messages"], function ($, messages) {

    var trigger_height = 0;
    var doit, document_height;
    var move_dashboard;

    function init() {
        add_responsive_behavior();
        check_dashboard_state();
        messages.display_messages();

        $(".logout").click(function () {
            $.ajax({
                url: root + "/sessions",
                type: "DELETE",
                success: success,
                error: error
            });
        });

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
            check_dashboard_state();
        }
    }

    function check_dashboard_state() {
        if ($(window).width() >= 640) {
            trigger_height = $("#dashboard").height() + 40 - $(window).height();
            if (trigger_height > 0) {
                move_dashboard = true;
                $("#dashboard").css("position", "absolute");
                $("#dashboard").css("top", "42px");
            } else {
                move_dashboard = false;
                $("#dashboard").css("position", "fixed");
                $("#dashboard").css("top", "42px");
            }
        }
    }

    function success() {
        document.location.reload();
    }

    function error() {

    }

    return {
        init: init
    };
});