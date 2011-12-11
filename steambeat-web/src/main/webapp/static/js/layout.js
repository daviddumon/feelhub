$(function () {

    var expand_header = false;
    $("#expand_button").click(function () {
        if (expand_header) {
            $("#expand_button").html("more");
            $("#right_panel").css("width","0%");
            $("#expand_button").css("left","34%");
            expand_header = false;
        } else {
            $("#right_panel").animate({width:"62%"}, 600);
            $("#expand_button").animate({left:"96%"}, 600);
            $("#expand_button").html("less");
            expand_header = true;
        }
    });

    //$("#help").delay(2000).queue(function(){
    //$("#help").hide();
    //$(".opinion").animate({top: '+=200'}, 500);
    //});
});
