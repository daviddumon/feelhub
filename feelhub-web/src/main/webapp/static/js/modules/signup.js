/* Copyright Feelhub 2012 */
define(["jquery", "modules/messages"], function ($, messages) {

    function init() {
        $(".help_text").click(function () {
            $(this).parent().find("input").focus();
        });

        $("input").keypress(function () {
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
                            $("[name='email']").parent().find(".error_text").text("Please enter a real email!");
                            messages.draw_message("bad", "Please enter a real email!", 3);
                        } else if (jqXHR.status == 409) {
                            $("[name='email']").parent().find(".error_text").text("This email is already used!");
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
            $("[name='fullname']").parent().find(".error_text").text("Please enter your name!");
            messages.draw_message("bad", "Please enter your name!", 3);
            return false;
        } else {
            $("[name='fullname']").parent().find(".error_text").text("");
            return true;
        }
    }

    function check_email() {
        if ($("[name='email']").val().length == 0) {
            $("[name='email']").parent().find(".error_text").text("Please enter your email!");
            messages.draw_message("bad", "Please enter your email!", 3);
            return false;
        } else {
            $("[name='email']").parent().find(".error_text").text("");
            return true;
        }
    }

    function check_password() {
        if ($("[name='password']").val().length < 6) {
            $("[name='password']").parent().find(".error_text").text("Password must be at least 6 characters!");
            messages.draw_message("bad", "Password must be at least 6 characters!", 3);
            return false;
        } else {
            $("[name='password']").parent().find(".error_text").text("");
            return true;
        }
    }

    return {
        init: init
    }

});