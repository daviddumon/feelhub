define(["jquery", "plugins/hgn!templates/command/new_feeling_topics_topic"], function ($, template) {

    var container = "#form-topics";
    var sentiment_width = 96; //margin * 2 + width + div space
    var sentiment_height = 74; //margin * 2 + width + div space
    var base_height = 190;

    $("html").on("click", container + " div", function () {
        var name = $(this).attr("topic-name");
        $("#form-right-panel").trigger("topic-click", name);
    });

    function render(data) {
        data.root = root;
        var topic = template(data);
        $(container).append(topic);
        check_size();
    }

    function reset() {
        $(container).empty();
        check_size();
    }

    function check_size() {
        var current_width = $(container).width();
        var sentiment_count = $(".topic-form").length;
        var row = Math.floor(current_width / sentiment_width);
        var new_height = Math.ceil(sentiment_count / row) * sentiment_height;
        new_height = new_height > 0 ? new_height : 72;
        $("#feeling-form").height(new_height + base_height);
    }

    return {
        render: render,
        reset: reset
    }
});