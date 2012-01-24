/* Copyright bytedojo 2011 */
$(function () {

    $("#to_top").click(function () {
        $('html, body').animate({scrollTop:'0px'}, 300, function () {
            $("#to_top").fadeOut(300);
        });
    });
});