/* Copyright bytedojo 2011 */
$(function () {

    $("#scroll_to_top").click(function () {
        $('html, body').animate({scrollTop:'0px'}, 300, function () {
            $("#scroll_to_top").fadeOut(300);
        });
    });

    $(window).scroll(function () {
        if ($(window).scrollTop() > 200 && $("#scroll_to_top").is(":hidden")) {
            $("#scroll_to_top").fadeIn(300);
        }
        if ($(window).scrollTop() < 200 && $("#scroll_to_top").is(":visible")) {
            $("#scroll_to_top").fadeOut(300);
        }
    });

    //$("#about_dialog").dialog({
    //    modal:true,
    //    autoOpen:false
    //});
    //
    //$("#help_dialog").dialog({
    //    modal:true,
    //    autoOpen:false
    //});

    //$("#about_button").click(function () {
    //    $("#about_dialog").dialog("open");
    //});
    //
    //$("#help_button").click(function () {
    //    $("#help_dialog").dialog("open");
    //});
});
