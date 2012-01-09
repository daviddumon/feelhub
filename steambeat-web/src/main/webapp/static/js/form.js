/* Copyright bytedojo 2011 */
$(function () {

    $("#opinion_form").mouseover(function(){
        $("#opinion_form_inner_wrapper").clearQueue();
        $("#opinion_form_inner_wrapper").slideDown(200);
        $("#opinion_form_textarea").focus();
    });

    $("#opinion_form").mouseleave(function(){
        $("#opinion_form_inner_wrapper").clearQueue();
        $("#opinion_form_inner_wrapper").delay(600).slideUp(200);
    });

    $("#opinion_wrapper").children().click(function () {
        if (!$("#opinion_form_textarea").is(":focus")) {
            $("#opinion_form_textarea").clearQueue();
            $("#opinion_form_textarea").focus();
            $("#opinion_form_judgments").clearQueue();
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

    //$("#add_opinion_form_judgments > .subject_tag").mouseover(function (event) {
    //    console.log("mouseover");
    //    var info = $(this).parent("div").find(".subject_info");
    //    info.css("top", event.pageY - 60 - $(window).scrollTop());
    //    info.css("left", event.pageX - (info.width() / 2));
    //    info.show();
    //});
    //$("#add_opinion_form_judgments > .subject_tag").mouseout(function (event) {
    //    console.log("mouseout");
    //    $(this).parent("div").find(".subject_info").hide();
    //});

});