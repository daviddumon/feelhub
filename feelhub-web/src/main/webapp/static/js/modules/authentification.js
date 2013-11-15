define(["jquery", "modules/messages"], function ($, messages) {

    function init() {

        $("input").keypress(function (event) {
            var code = event.keyCode || event.which;
            if (code == 13) {
                if ($("#signup").is(":visible")) {
                    signup();
                } else if ($("#login").is(":visible")) {
                    login();
                }
            }
        });

        $("#login_submit").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            login();
        });

        $("#signup_submit").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            signup();
        });

        $("#logout").click(function () {
            $.ajax({
                url: root + "/sessions",
                type: "DELETE",
                success: function () {
                    messages.store_message("bored", "Goodbye!", 3);
                    document.location.reload(true);
                },
                error: function () {
                    messages.store_message("sad", "There was a disturbance in the Force", 3);
                }
            });
        });
    }

    function login() {
        if (check_login_form()) {
            $.post(root + "/sessions?", $("#login form").serialize(),function (data, status, jqXHR) {
                messages.store_message("happy", "Welcome back!", 3);
                document.location.reload(true);
            }).error(function (jqXHR) {
                    if (jqXHR.status == 403) {
                        messages.draw_message("sad", jqXHR.responseText, 3);
                    } else if (jqXHR.status == 401) {
                        messages.draw_message("sad", "Wrong password!", 3);
                    } else if (jqXHR.status == 400) {
                        messages.draw_message("sad", "There was a disturbance in the Force!", 3);
                    } else if (jqXHR.status == 500) {
                        messages.draw_message("sad", "This user is unknown", 3);
                    }
                });
        }
    }

    function signup() {
        if (check_signup_form()) {
            $.post(root + "/signup?", $("#signup form").serialize(),function (data, status, jqXHR) {
                $.post(root + "/sessions?", $("#signup form").serialize(), function (data, status, jqXHR) {
                    messages.store_message("happy", "Welcome to Feelhub! We hope you will enjoy it :)", 3);
                    document.location.reload(true);
                });
            }).error(function (jqXHR) {
                    if (jqXHR.status == 412) {
                        messages.draw_message("sad", "Please enter a real email!", 3);
                    } else if (jqXHR.status == 409) {
                        messages.draw_message("sad", "This email is already used!", 3);
                    } else if (jqXHR.status == 400) {
                        messages.draw_message("sad", "There was a disturbance in the Force", 3);
                    }
                });
        }
    }

    function check_login_form() {
        var email_check = check_email("#login");
        var password_check = check_password("#login");
        return email_check && password_check;
    }

    function check_signup_form() {
        var name_check = check_name("#signup");
        var email_check = check_email("#signup");
        var password_check = check_password("#signup");
        return name_check && email_check && password_check;
    }

    function check_email(form) {
        if ($(form + " [name='email']").val().length == 0) {
            messages.draw_message("sad", "Please enter your email!", 3);
            return false;
        } else {
            return true;
        }
    }

    function check_password(form) {
        if ($(form + " [name='password']").val().length == 0) {
            messages.draw_message("sad", "Please enter your password!", 3);
            return false;
        } else {
            return true;
        }
    }

    function check_name(form) {
        if ($(form + " [name='fullname']").val().length == 0) {
            messages.draw_message("sad", "Please enter your name!", 3);
            return false;
        } else {
            return true;
        }
    }

    return {
        init: init
    }
});