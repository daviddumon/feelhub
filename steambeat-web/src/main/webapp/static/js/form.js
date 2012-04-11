/* Copyright bytedojo 2011 */
$(function () {

    //$("#opinion_form_textarea").click(function () {
    //    $("#opinion_form_judgments").clearQueue();
    //    $("#opinion_form_textarea").clearQueue();
    //    $("#opinion_form_judgments").slideDown(200);
    //    $("#opinion_form_textarea").animate({"height":"80px"}, 200);
    //    $("#opinion_form_textarea").focus();
    //});
    //
    //$("#opinion_form").mouseover(function () {
    //    $("#opinion_form_judgments").clearQueue();
    //    $("#opinion_form_textarea").clearQueue();
    //});
    //
    //$("#opinion_form").mouseleave(function () {
    //    if ($("#opinion_form_judgments").is(":visible")) {
    //        $("#opinion_form_judgments").clearQueue();
    //        $("#opinion_form_textarea").clearQueue();
    //        $("#opinion_form_judgments").delay(1000).slideUp(200);
    //        $("#opinion_form_textarea").delay(1000).animate({"height":"40px"}, 200);
    //    }
    //});
    //
    //$("#opinion_form").children().click(function () {
    //    if (!$("#opinion_form_textarea").is(":focus")) {
    //        $("#opinion_form_textarea").focus();
    //    }
    //});

    $("#opinion_form .judgment").click(function () {
        $(this).children(".judgment_tag").toggleClass("good_border");
        $(this).children(".judgment_tag").toggleClass("bad_border");
        $(this).children(".feeling_smiley").toggleClass("good");
        $(this).children(".feeling_smiley").toggleClass("bad");
        if($(this).children().hasClass("bad_border")) {
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

function openOpinionForm() {
    $("#blanket").css("height", $(window).height());
    $("#blanket").show();
    $("#opinion_form").css("top", 50);
    $("#opinion_form").css("left", ($(window).width() - $("#opinion_form").width()) / 2);
    $("#opinion_form").css("height", $(window).height() - 100);
    $("#opinion_form").show();
}

function closeOpinionForm() {
    $("#opinion_form").hide();
    $("#blanket").hide();
}