$(function () {

    var expand_header = false;
    $("#expand_button").click(function () {
        if (expand_header) {
            $("#expand_button").html("more");
            $("#right_panel").css("width","0%");
            $("#expand_button").css("left","34%");
            //$("#expand_button").css("margin-left","8px");
            expand_header = false;
        } else {
            $("#right_panel").animate({width:"62%"}, 600);
            $("#expand_button").animate({left:"96%"}, 600);
            //$("#expand_button").css("margin-left","7px");
            $("#expand_button").html("less");
            expand_header = true;
        }
    });

    $("#add_opinion").click(function() {
        $("#add_opinion").css("height", "200px");
    });

    //$("#help").delay(2000).queue(function(){
    //$("#help").hide();
    //$(".opinion").animate({top: '+=200'}, 500);
    //});
});
