define(["jquery", "modules/welcome", "modules/bookmarkletinstall"], function ($, welcome, bookmarkletinstall) {

    var overlay_container = "#overlay";
    var signup_button = "#signup-button";
    var login_button = "#login-button";
    var close_button = ".close-button";
    var login_popup = "#login";
    var signup_popup = "#signup";
    var welcome_popup = "#welcome";
    var bookmarkletinstall_popup = "#bookmarkletinstall";
    var popups = ".popup";
    var canClose = true;

    if ($(welcome_popup).length > 0) {
        welcome.init();
        show_popup(welcome_popup);
    }

    if ($(bookmarkletinstall_popup).length > 0) {
        bookmarkletinstall.init();
        show_popup(bookmarkletinstall_popup);
    }

    $("body").on("click", close_button, function (event) {
        if (canClose) {
            close_popup();
        }
    });

    $("body").on("needlogin", function (event) {
        canClose = false;
        show_popup(login_popup);
    });

    $("body").on("click", overlay_container, function (event) {
        if (canClose) {
            close_popup();
        }
    });

    $("body").on("click", login_button, function (event) {
        show_popup(login_popup);
        $("#login input[name=email]").focus();
    });

    $("body").on("click", signup_button, function (event) {
        show_popup(signup_popup);
        $("#signup input[name=fullname]").focus();
    });

    $(document).keydown(function (event) {
        var code = event.keyCode || event.which;
        if (code == 27) {
            if ($(overlay_container).is(":visible")) {
                if (canClose) {
                    close_popup();
                }
            }
        }
    });

    function show_popup(name) {
        close_popup();
        show_overlay();
        if (canClose) {
            $(name + " " + close_button).show();
        }
        $(name).css("top", $(window).height() / 2 - $(name).height() / 2);
        $(name).show();
    }

    function show_overlay() {
        if ($(overlay_container).is(":hidden")) {
            $(overlay_container).show();
        }
    }

    function close_popup() {
        $(overlay_container).hide();
        $(popups).each(function () {
            if ($(this).is(":visible")) {
                $(this).hide();
            }
        });
    }
});