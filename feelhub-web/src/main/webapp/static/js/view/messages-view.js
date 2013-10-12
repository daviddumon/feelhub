define(["jquery", "plugins/hgn!templates/message"], function ($, template) {

    var container = "#messages";

    function render(data) {
        var message = template(data);
        $(container).append(message);
        if (data.timer > 0) {
            $("#message_" + data.index).fadeIn(600).delay(data.timer * 1000).fadeOut(600);
        } else {
            $("#message_" + data.index).fadeIn(600);
        }
    }

    function clear() {
        $(container).empty();
    }

    return {
        render: render,
        clear: clear
    }
});