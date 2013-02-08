define(["jquery", "plugins/hgn!templates/command/new_feeling_submit"], function ($, template) {

    var container = "#form-right-panel";

    function render() {
        $(container).empty();
        var submit = template({"root": root});
        $(container).append(submit);
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