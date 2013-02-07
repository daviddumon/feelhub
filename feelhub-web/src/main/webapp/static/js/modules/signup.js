/* Copyright Feelhub 2012 */
define(['jquery'], function ($) {

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
            if (checkForm()) {
                $.post(root + "/signup?", $("#signup").serialize(),function (data, status, jqXHR) {
                    document.location.href = root + "/welcome";
                }).error(function (jqXHR) {
                        if (jqXHR.status == 412) {
                            $("[name='email']").parent().find(".error_text").text("Please enter a real email!");
                        } else if (jqXHR.status == 409) {
                            $("[name='email']").parent().find(".error_text").text("This email is already used!");
                        } else if (jqXHR.status == 400) {

                        }
                    });
            }
        });
    }

    function checkForm() {
        var name_check = checkName();
        var email_check = checkEmail();
        var password_check = checkPassword();
        return name_check && email_check && password_check;
    }

    function checkName() {
        if ($("[name='fullname']").val().length == 0) {
            $("[name='fullname']").parent().find(".error_text").text("Please enter your name!");
            return false;
        } else {
            $("[name='fullname']").parent().find(".error_text").text("");
            return true;
        }
    }

    function checkEmail() {
        if ($("[name='email']").val().length == 0) {
            $("[name='email']").parent().find(".error_text").text("Please enter your email!");
            return false;
        } else {
            $("[name='email']").parent().find(".error_text").text("");
            return true;
        }
    }

    function checkPassword() {
        if ($("[name='password']").val().length < 6) {
            $("[name='password']").parent().find(".error_text").text("Password must be at least 6 characters!");
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