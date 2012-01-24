/* Copyright bytedojo 2011 */
$(function () {

    $("#opinion_form_textarea").click(function(){
        $("#opinion_form_judgments").clearQueue();
        $("#opinion_form_textarea").clearQueue();
        $("#opinion_form_judgments").slideDown(200);
        $("#opinion_form_textarea").animate({"height":"80px"}, 200);
        $("#opinion_form_textarea").focus();
    });

    $("#opinion_form").mouseover(function(){
        $("#opinion_form_judgments").clearQueue();
        $("#opinion_form_textarea").clearQueue();
    });

    $("#opinion_form").mouseleave(function(){
        if($("#opinion_form_judgments").is(":visible")) {
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

    $("#opinion_form_judgments > .good").click(function () {
        $(this).toggleClass("good");
        $(this).toggleClass("bad");
        $(this).parent().find("input").attr("value", "bad");
    });

    $("#opinion_form_judgments > .bad").click(function () {
        $(this).toggleClass("bad");
        $(this).toggleClass("good");
        $(this).parent().find("input").attr("value", "good");
    });

    $("#opinion_form_submit").click(function (event) {
        event.preventDefault();
        event.stopImmediatePropagation();
        var form = $("#opinion_form").serializeArray();
        $.post(formAction, form, function (data, textStatus, jqXHR) {
            location.href = jqXHR.getResponseHeader("Location");
        });
        return false;
    });
});