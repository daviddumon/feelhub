/* Copyright bytedojo 2011 */
$(function() {
    $("#help").hide();

    $("#displayhelp").click(function(){
        if($("#help").is(":hidden")) {
            $("#help").slideDown("slow", function(){
                $("#displayhelp > em").text("hide help");
            });
        } else {
            $("#help").slideUp("slow", function() {
                $("#displayhelp > em").text("show help");
            });
        }
    });
});