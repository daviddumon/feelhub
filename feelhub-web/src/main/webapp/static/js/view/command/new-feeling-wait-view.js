define(["jquery", "plugins/hgn!templates/command/new_feeling_wait"], function ($, template) {

    var container = "#form-right-panel";

    function render() {
        $(container).empty();
        var question = template({"root": root});
        $(container).append(question);
    }

    return {
        render: render
    }
});