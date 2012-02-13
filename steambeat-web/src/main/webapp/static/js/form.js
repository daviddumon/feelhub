/* Copyright bytedojo 2011 */
$(function () {

    $("#opinion_form_textarea").click(function () {
        $("#opinion_form_judgments").clearQueue();
        $("#opinion_form_textarea").clearQueue();
        $("#opinion_form_judgments").slideDown(200);
        $("#opinion_form_textarea").animate({"height":"80px"}, 200);
        $("#opinion_form_textarea").focus();
    });

    $("#opinion_form").mouseover(function () {
        $("#opinion_form_judgments").clearQueue();
        $("#opinion_form_textarea").clearQueue();
    });

    $("#opinion_form").mouseleave(function () {
        if ($("#opinion_form_judgments").is(":visible")) {
            $("#opinion_form_judgments").clearQueue();
            $("#opinion_form_textarea").clearQueue();
            $("#opinion_form_judgments").delay(1000).slideUp(200);
            $("#opinion_form_textarea").delay(1000).animate({"height":"40px"}, 200);
        }
    });

    $("#opinion_form").children().click(function () {
        if (!$("#opinion_form_textarea").is(":focus")) {
            $("#opinion_form_textarea").focus();
        }
    });

    $("#opinion_form_judgments > .judgment_tag").click(function () {
        $(this).toggleClass("good");
        $(this).toggleClass("bad");
        if($(this).hasClass("bad")) {
            $(this).children("[name='feeling']").attr("value", "bad");
        } else {
            $(this).children("[name='feeling']").attr("value", "good");
        }
    });

    $("#opinion_form_submit").click(function (event) {
        event.preventDefault();
        event.stopImmediatePropagation();
        var form = $("#opinion_form").serializeArray();
        $.post(root + "/opinions", form, function (data, textStatus, jqXHR) {
            location.href = jqXHR.getResponseHeader("Location");
        });
        return false;
    });
});