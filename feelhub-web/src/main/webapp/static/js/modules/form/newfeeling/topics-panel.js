define(["jquery", "view/command/new-feeling-topics-topic-view"], function ($, view) {

    var uri = /((http|https):\/\/)?([%a-zA-Z_0-9\.-]+)(\.[a-z]{2,3}){1}(\/.*$)?/i;
    var topics = [];

    $("html").on("sentiment-change", "#form-left-panel", function (event, data) {
        var index = index_of(data.name);
        if (index != -1) {
            topics[index].sentiment = data.sentiment;
            topics[index].unset = false;
        }
        re_draw();
    });

    $("html").on("set-topic", "#form-left-panel", function (event, data) {
        var index = index_of(data.name);
        if (index != -1) {
            topics[index].id = data.id;
            topics[index].thumbnailSmall = data.thumbnailSmall;
            if (data.id === "new" && !uri.test(data.name)) {
                $("#form-right-panel").trigger("category-question", topics[index]);
            }
        }
        re_draw();
    });

    $("html").on("set-category", "#form-left-panel", function (event, data) {
        var index = index_of(data.name);
        if (index != -1) {
            topics[index].type = data.type;
        }
    });

    function re_draw() {
        view.reset();
        $.each(topics, function (index, topic) {
            if (topic.active == true) {
                view.render(topic);
            }
        });
    }

    function add_topic(data, permanent) {
        if (data.name != null && data.name.length > 0) {
            data.sentiment = data.sentiment || "none";
            data.id = data.id || "";
            data.thumbnailSmall = data.thumbnailSmall || "";
            data.active = true;
            data.permanent = permanent || false;
            data.unset = true;
            var topic = create(data);
            view.render(topic);
            return topic;
        }
    }

    function create(data) {
        var topic_index = index_of(data.name);
        if (topic_index < 0) {
            topics.push(data);
            return topics[topics.length - 1];
        } else {
            topics[topic_index].active = true;
            if (topics[topic_index].unset == true) {
                topics[topic_index].sentiment = data.sentiment;
            }
            return topics[topic_index];
        }
    }

    function index_of(name) {
        var topic_index = -1;
        $.each(topics, function (index, topic) {
            if (topic.name === name) {
                topic_index = index;
            }
        });
        return topic_index;
    }

    function invalidate() {
        view.reset();
        $.each(topics, function (index, topic) {
            if (!topic.permanent && topic.active == true) {
                topic.active = false;
            }
            if (topic.active == true) {
                view.render(topic);
            }
        });
    }

    function get_topics() {
        return topics;
    }

    //testing
    function test_reset() {
        topics = [];
    }

    return {
        add_topic: add_topic,
        invalidate: invalidate,
        get_topics: get_topics,
        //test purpose
        test_reset: test_reset
    }
});