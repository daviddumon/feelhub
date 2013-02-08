define(["jquery", "plugins/hgn!templates/command/new_feeling_helper_category"], function ($, template) {

    var container = "#form-right-panel";

    $("html").on("click", container + " .category-ok", function () {
        var data = {
            "name": $(".question-name").html(),
            "type": $(".category-input").val()
        };
        $("#form-left-panel").trigger("set-category", data);
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
        $(container).empty();
    }

    return {
        render: render,
        clear: clear
    }

});