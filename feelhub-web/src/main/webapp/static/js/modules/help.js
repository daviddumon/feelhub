define(["jquery"],

    function ($) {

        $("#help #menu a").first().toggleClass("selected");
        $("#help-content div").first().show();

        $("body").on("click", "#help #menu a", function () {
            $("#help #menu a").each(function (index, link) {
                $(link).removeClass("selected");
            });
            $(this).toggleClass("selected");
            $("#help-content div").each(function () {
                $(this).hide();
            });
            $($(this).attr("name") + "-content").show();
        });
    });