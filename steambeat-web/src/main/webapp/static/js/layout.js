$(function () {

    $("#about_dialog").dialog({
        modal:true,
        autoOpen:false
    });

    $("#help_dialog").dialog({
        modal:true,
        autoOpen:false
    });

    $("#about_button").click(function () {
        $("#about_dialog").dialog("open");
    });

    $("#help_button").click(function () {
        $("#help_dialog").dialog("open");
    });

    $("#arrow_up").click(function () {
        $('html, body').animate({scrollTop:'0px'}, 300, function () {
            $("#arrow_up").fadeOut(300);
        });
    });

    $(window).scroll(function () {
        if ($(window).scrollTop() > 200 && $("#arrow_up").is(":hidden")) {
            $("#arrow_up").fadeIn(300);
        }
        if ($(window).scrollTop() < 200 && $("#arrow_up").is(":visible")) {
            $("#arrow_up").fadeOut(300);
        }
    });

    $("#add_opinion_form").focusin(function () {
        $("#add_opinion_form_textarea").animate({height:'200px'}, 200);
        $("#add_opinion_form_judgments").show();
    });

    $("#add_opinion_form").focusout(function () {
        $("#add_opinion_form_textarea").delay(200).animate({height:'40px'}, 100);
        $("#add_opinion_form_judgments").delay(200).fadeOut(0);
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
