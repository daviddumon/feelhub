define(["jquery", "plugins/hgn!templates/command/new_feeling_helper_feel"], function ($, template) {

    var container = "#form-right-panel";

    $("html").on("click", container + " .sentiment-choice img", function () {
        var data = {
            "name": $(this).attr("topic-name"),
            "sentiment": $(this).attr("sentiment")
        };
        $("#form-left-panel").trigger("sentiment-change", data);
        $("#form-right-panel").trigger("next-question");
    });

    function render(data) {
        $(container).empty();
        data.root = root;
        var question = template(data);
        $(container).append(question);
        $(".form-help").fadeIn(1000);
    }

    function clear() {
        $(".form-help").fadeOut(1000);
        $("#form-right-panel").empty();
    }

    return {
        render: render,
        clear: clear
    }
});