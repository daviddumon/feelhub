define(["jquery", "modules/welcome", "modules/bookmarkletinstall", "modules/authentification"], function ($, welcome, bookmarkletinstall, authentification) {

    var overlay_container = "#overlay";

    var signup_button = ".signup-button";
    var login_button = ".login-button";
    var close_button = ".close-button";
    var add_button = ".add-topic-button";

    var login_popup = "#login";
    var signup_popup = "#signup";
    var welcome_popup = "#welcome";
    var add_popup = "#add-topic";
    var bookmarkletinstall_popup = "#bookmarkletinstall";

    var popups = ".popup";
    var canClose = true;

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

    $("body").on("click", add_button, function (event) {
        //show_popup(add_popup);
    });

    $("body").on("show-welcome", function (event) {
        welcome.init();
        show_popup(welcome_popup);
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

    function init() {
        authentification.init();

        if ($(bookmarkletinstall_popup).length > 0) {
            bookmarkletinstall.init();

            // vilain hack => la structure des popups est a revoir pour tenir compte de l'affichage special welcome and bookmarklet
            if ($(bookmarkletinstall_popup).length > 0) {
                show_popup(bookmarkletinstall_popup);
                $(bookmarkletinstall_popup).css("top", "82px");
            }
        }
    }

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
        $(popups).each(function (index, popup) {
            if ($(this).is(":visible")) {
                $(this).hide();
            }
        });
    }

    return {
        init: init
    }
});