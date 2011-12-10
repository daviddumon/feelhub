$(function () {

    var expand_header = false;
    $("#expand_button").click(function () {
        if (expand_header) {
            $("header").animate({width:"30%"}, 600);
            $("#expand_button").animate({left:"34%"}, 600);
            $("#expand_button_inner").html("more");
            expand_header = false;
        } else {
            $("header").animate({width:"92%"}, 600);
            $("#expand_button").animate({left:"96%"}, 600);
            $("#expand_button_inner").html("less");
            expand_header = true;
        }
    });

    //$("#help").delay(2000).queue(function(){
    $("#help").hide();
    //$(".opinion").animate({top: '+=200'}, 500);
    //});
});
