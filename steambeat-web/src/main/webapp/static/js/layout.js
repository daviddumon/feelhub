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

    $("#add_opinion").click(function () {
        $("#add_opinion").css("height", "200px");
    });
});
