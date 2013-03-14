define(["jquery", "plugins/hgn!templates/command/new_feeling_topics_topic"], function ($, template) {

    var container = "#form-topics";
    var topic_width = 96; //margin * 2 + width + div space
    var topic_height = 74; //margin * 2 + width + div space
    var base_height = 186;

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
        var topic_count = $(".topic-form").length;
        var cols = Math.floor($(container).width() / topic_width);
        var rows = Math.ceil(topic_count / cols);
        var new_height =  rows * topic_height;
        new_height = new_height > 0 ? new_height : topic_height;
        $("#feeling-form").height(new_height + base_height);
    }

    return {
        render: render,
        reset: reset
    }
});