/* Copyright Feelhub 2012 */
define(["jquery", "modules/messages"], function ($, messages) {

    function init() {
        $(".help_text").click(function () {
            $(this).parent().find("input").focus();
        });

        $("input").keypress(function (event) {
            $(this).parent().find(".help_text").hide();
            var code = event.keyCode || event.which;
            if (code == 13) {
                login();
            }
        });

        $("input").focusout(function () {
            if ($(this).val() == "") {
                $(this).parent().find(".help_text").show();
            }
        });

        $("#login_submit").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            login();
        });
    }

    function login() {
        if (check_form()) {
            $.post(root + "/sessions?", $("#login").serialize(),function (data, status, jqXHR) {
                messages.store_message("good", "Welcome back!", 3);
                document.location.href = referrer;
            }).error(function (jqXHR) {
                    if (jqXHR.status == 403) {
                        messages.draw_message("bad", jqXHR.responseText, 3);
                    } else if (jqXHR.status == 401) {
                        messages.draw_message("bad", "Wrong password!", 3);
                    } else if (jqXHR.status == 400) {
                        messages.draw_message("bad", "There was a disturbance in the Force!", 3);
                    } else if (jqXHR.status == 500) {
                        messages.draw_message("bad", "This user is unknown", 3);
                    }
                });
        }
    }

    function check_form() {
        var email_check = check_email();
        var password_check = check_password();
        return email_check && password_check;
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
        if ($("[name='password']").val().length == 0) {
            messages.draw_message("bad", "Please enter your password!", 3);
            return false;
        } else {
            return true;
        }
    }

    return {
        init: init
    }

});