/* Copyright Steambeat 2012 */
$(function () {

    $(".help_text").click(function () {
        $(this).parent().find("input").focus();
    });

    $("input").focusin(function () {
        $(this).parent().find(".help_text").css("color", "#CCCCCC");
    });

    $("input").keypress(function () {
        $(this).parent().find(".help_text").hide();
    });

    $("input").focusout(function () {
        if ($(this).attr("value") == "") {
            $(this).parent().find(".help_text").show();
        }
        $(this).parent().find(".help_text").css("color", "#999999");
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
                        console.log("error : " + jqXHR.status + " while posting on " + root + "/signup");
                    }
                });
        }
    });
});

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