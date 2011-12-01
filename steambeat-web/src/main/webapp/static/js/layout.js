$(function(){
    $("#more").click(function(){
    $("header").animate({width: "90%"}, 600);
    $("#more").animate({left: "95%"}, 600);
    //$("header").animate({width: "90%"}, 600);

        console.log($("header").css("width"));
        if($("header").css("width") == '30%') {
            console.log("ok");
            $("header").animate({top: "100px"}, 1000);
            //$("#helpbutton").animate({top: '+=10'}, 200, function() {
            //    $("#helpbutton").animate({top: '-=10'}, 100, function(){
            //        $("#help").css("height","250px");
            //        $("#help").slideDown("slow", function(){});
            //    });
            //});
        } else {
            //$("#helpbutton").animate({top: '-=10'}, 200, function() {
            //    $("#helpbutton").animate({top: '+=10'}, 100, function(){
            //        $("#help").slideUp("slow", function() {
            //            $("#help").css("height","0px");
            //        });
            //    });
            //});
        }
    });
});
