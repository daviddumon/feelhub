$(function () {

    $("#add_opinion").click(function () {
        $("#add_opinion").css("height", "200px");
    });

    $("#arrow_up").click(function () {
        $('html, body').animate({scrollTop:'0px'}, 300, function () {
            $("#arrow_up").fadeOut(300);
        });
    });

    $(window).scroll(function () {
        if ($(window).scrollTop() > 100) {
            $("#arrow_up").fadeIn(300);
        }
    });

    //$("#help").delay(2000).queue(function(){
    //$("#help").hide();
    //$(".opinion").animate({top: '+=200'}, 500);
    //});
});
