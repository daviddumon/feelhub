/* Copyright Feelhub 2012 */
define(['jquery'], function ($) {

    function init() {
        $(".help_text").click(function () {
            $(this).parent().find("input").focus();
        });

        $("input").focusin(function () {
            $(this).parent().find(".help_text").css("color", "#CCCCCC");
        });

        $("input").keypress(function (event) {
            $(this).parent().find(".help_text").hide();
            var code = event.keyCode || event.which;
            if (code == 13) {
                login();
            }
        });

        $("input").change(function (event) {
            if ($(this).val() !== "") {
                $(this).parent().find(".help_text").hide();
            }
        });

        $("input").focusout(function () {
            if ($(this).val() == "") {
                $(this).parent().find(".help_text").show();
            }
            $(this).parent().find(".help_text").css("color", "#999999");
        });

        $("#login_submit").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            login();
        });
    }

    function checkForm() {
        var email_check = checkEmail();
        var password_check = checkPassword();
        return email_check && password_check;
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

    function login() {
        if (checkForm()) {
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