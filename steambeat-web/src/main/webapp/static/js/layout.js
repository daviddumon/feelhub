/* Copyright bytedojo 2011 */
$(function() {

    $("#submitsearch").click(function(e) {
        var form = $("#formsearch").get(0);
        if(form.checkValidity()) {
            var uri = $("#searchstring").val();
            e.preventDefault();
            e.stopImmediatePropagation();
            alert("soon :)");
            //if(uri.length > 0) {
            //    location.href = $("#submitsearch").attr("action") + uri;
            //}
        }
    });

    $("#searchstring").focus(function() {
        if($(this).val() == "search") {
            $(this).val("");
        }
    });

    $("#help").hide();
    $("#help").css("height","0px");

    $("#helpbutton").click(function(){
        if($("#help").is(":hidden")) {
            $("#helpbutton").animate({top: '+=10'}, 200, function() {
                $("#helpbutton").animate({top: '-=10'}, 100, function(){
                    $("#help").css("height","250px");
                    $("#help").slideDown("slow", function(){});
                });
            });
        } else {
            $("#helpbutton").animate({top: '-=10'}, 200, function() {
                $("#helpbutton").animate({top: '+=10'}, 100, function(){
                    $("#help").slideUp("slow", function() {
                        $("#help").css("height","0px");
                    });
                });
            });
        }
    });
});