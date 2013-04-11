define(["jquery", "plugins/hgn!templates/command/new_feeling_helper_choice"], function ($, template) {

    var container = "#form-right-panel";

    $("html").on("click", "#form-right-panel .topic-choice div", function () {
        var data = {
            "name": $(this).attr("topic-name"),
            "id": $(this).attr("topic-id"),
            "thumbnail": $(this).attr("topic-thumbnail"),
            "type": $(this).attr("topic-type")
        };
        $("#form-right-panel").trigger("next-question");
        $("#form-left-panel").trigger("set-topic", data);
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
        $(container).empty();
    }

    return {
        render: render,
        clear: clear
    }
});