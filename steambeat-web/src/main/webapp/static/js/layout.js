$(function () {

    $("#expand_button").click(function () {
        if ($("#expand_button").css("left") == '34%') {
            $("header").animate({width:"92%"}, 600);
            $("#expand_button").animate({left:"96%"}, 600);
            $("#expand_button_inner").html("less");
        } else {
            $("header").animate({width:"30%"}, 600);
            $("#expand_button").animate({left:"34%"}, 600);
            $("#expand_button_inner").html("more");
        }
    });

    //$("#help").delay(2000).queue(function(){
    $("#help").hide();
    //$(".opinion").animate({top: '+=200'}, 500);
    //});
});
