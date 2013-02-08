/* Copyright Feelhub 2012 */
define(["jquery"], function ($) {

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

    function check_form() {
        var email_check = check_email();
        var password_check = check_password();
        return email_check && password_check;
    }

    function check_email() {
        if ($("[name='email']").val().length == 0) {
            $("[name='email']").parent().find(".error_text").text("Please enter your email!");
            return false;
        } else {
            $("[name='email']").parent().find(".error_text").text("");
            return true;
        }
    }

    function check_password() {
        if ($("[name='password']").val().length < 6) {
            $("[name='password']").parent().find(".error_text").text("Password must be at least 6 characters!");
            return false;
        } else {
            $("[name='password']").parent().find(".error_text").text("");
            return true;
        }
    }

    function login() {
        if (check_form()) {
            $.post(root + "/sessions?", $("#login").serialize(),function (data, status, jqXHR) {
                document.location.href = referrer;
            }).error(function (jqXHR) {
                    if (jqXHR.status == 403) {
                        $("[name='email']").parent().find(".error_text").text(jqXHR.responseText);
                    } else if (jqXHR.status == 401) {
                        $("[name='password']").parent().find(".error_text").text("Wrong password");
                    } else if (jqXHR.status == 400) {

                    } else if (jqXHR.status == 500) {
                        $("[name='email']").parent().find(".error_text").text("This user is unknown");
                    }
                });
        }
    }

    return {
        init: init
    }

});