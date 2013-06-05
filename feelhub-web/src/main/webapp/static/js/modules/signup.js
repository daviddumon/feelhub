/* Copyright Feelhub 2012 */
define(["jquery", "modules/messages"], function ($, messages) {

    function init() {
        $(".help_text").click(function () {
            $(this).parent().find("input").focus();
        });

        $("input").keypress(function () {
            var code = event.keyCode || event.which;
            if (code == 13) {
                login();
            }
        });

        $("#signup_submit").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            if (check_form()) {
                $.post(root + "/signup?", $("#signup").serialize(),function (data, status, jqXHR) {
                    $.post(root + "/sessions?", $("#signup").serialize(), function (data, status, jqXHR) {
                        messages.store_message("good", "Welcome to Feelhub!", 5);
                        document.location.href = root;
                    });
                }).error(function (jqXHR) {
                        if (jqXHR.status == 412) {
                            messages.draw_message("bad", "Please enter a real email!", 3);
                        } else if (jqXHR.status == 409) {
                            messages.draw_message("bad", "This email is already used!", 3);
                        } else if (jqXHR.status == 400) {
                            messages.draw_message("bad", "There was a disturbance in the Force", 3);
                        }
                    });
            }
        });
    }

    function check_form() {
        var name_check = check_name();
        var email_check = check_email();
        var password_check = check_password();
        return name_check && email_check && password_check;
    }

    function check_name() {
        if ($("[name='fullname']").val().length == 0) {
            messages.draw_message("bad", "Please enter your name!", 3);
            return false;
        } else {
            return true;
        }
    }

    function check_email() {
        if ($("[name='email']").val().length == 0) {
            messages.draw_message("bad", "Please enter your email!", 3);
            return false;
        } else {
            return true;
        }
    }

    function check_password() {
        if ($("[name='password']").val().length < 6) {
            messages.draw_message("bad", "Password must be at least 6 characters!", 3);
            return false;
        } else {
            return true;
        }
    }

    return {
        init: init
    }

});