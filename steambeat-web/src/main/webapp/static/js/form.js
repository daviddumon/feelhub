/* Copyright bytedojo 2011 */
$(function () {

    $("#add_opinion_form").mouseover(function(){
        $("#form_inner_wrapper").slideDown(200);
        $("#add_opinion_form_textarea").focus();
    });

    $("#add_opinion_form").mouseleave(function(){
        $("#form_inner_wrapper").delay(200).fadeOut(0);
    });

    $("#add_opinion_wrapper").children().click(function () {
        if (!$("#add_opinion_form_textarea").is(":focus")) {
            $("#add_opinion_form_textarea").clearQueue();
            $("#add_opinion_form_textarea").focus();
            $("#add_opinion_form_judgments").clearQueue();
        }
    });

    $("#add_opinion_form_judgments > .good").click(function () {
        $(this).toggleClass("good");
        $(this).toggleClass("bad");
        $(this).parent().find("input").attr("value", "bad");
    });

    $("#add_opinion_form_judgments > .bad").click(function () {
        $(this).toggleClass("bad");
        $(this).toggleClass("good");
        $(this).parent().find("input").attr("value", "good");
    });

    $("#add_opinion_form_submit").click(function (event) {
    console.log("coucou");
        event.preventDefault();
        event.stopImmediatePropagation();
        var form = $("#add_opinion_form").serializeArray();
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