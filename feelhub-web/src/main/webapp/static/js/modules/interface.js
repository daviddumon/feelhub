define(["jquery", "modules/messages", "modules/login"], function ($, messages, login) {

    var trigger_height = 0;
    var doit, document_height;
    var move_dashboard;

    function init() {
        add_responsive_behavior();
        check_dashboard_state();
        messages.init();
        login.init();

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

        $(".fixed-panel").css("top", $(window).height() / 2 - $(".fixed-panel").height() / 2);
        $(".fixed-panel").show();

        canCloseLogin = false;

        $(".login-button").click(function (event) {
            if ($("#login").is(":hidden")) {
                $("#login").show();
                $("#signup").hide();
                $("#login input[name=email]").focus();
                canCloseLogin = true;
                canCloseSignup = false;
                event.stopImmediatePropagation();
            } else {
                $("#login").hide();
            }
        });

        $("#login").hover(function (event) {
            canCloseLogin = false;
        }, function (event) {
            canCloseLogin = true;
        });

        canCloseSignup = false;

        $(".signup-button").click(function (event) {
            if ($("#signup").is(":hidden")) {
                $("#signup").show();
                $("#login").hide();
                $("#signup input[name=fullname]").focus();
                canCloseSignup = true;
                canCloseLogin = false;
                event.stopImmediatePropagation();
            } else {
                $("#signup").hide();
            }
        });

        $("#signup").hover(function (event) {
            canCloseSignup = false;
        }, function (event) {
            canCloseSignup = true;
        });

        $(document).click(function (event) {
            if (canCloseLogin) {
                $("#login").hide();
                canCloseLogin = false;
            }
            if (canCloseSignup) {
                $("#signup").hide();
                canCloseSignup = false;
            }
        });

        $(document).keydown(function (event) {
            var code = event.keyCode || event.which;
            if (code == 27) {
                if ($("#login").is(":visible")) {
                    $("#login").hide();
                }
                if ($("#signup").is(":visible")) {
                    $("#signup").hide();
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
            $(".fixed-panel").css("top", $(window).height() / 2 - $(".fixed-panel").height() / 2);
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