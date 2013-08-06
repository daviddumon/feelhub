define(["jquery", "plugins/hgn!templates/flow/flow_feeling", "plugins/hgn!templates/flow/flow_counter_feeling", "modules/canvas"],

    function ($, feeling, counter, canvas) {

        var last_feeling = "";
        var last_counter = "";

        function render(data, container, width) {
            if (data.text.length == 1 && data.text[0] == "") {
                render_counter(data, container);
            } else {
                render_feeling(data, container, width);
            }
        }

        function render_feeling(data, container, width) {
            var element = feeling(prepare_data(data, width));
            $(container).append(element);
            last_feeling = data.feelingid;
            var sentiment;
            if (data.sentimentDatas.length > 0) {
                sentiment = data.sentimentDatas[0].sentimentValue;
            } else {
                sentiment = data.feeling_sentiment_value;
            }

            if (sentiment == "bad") {
                canvas.youfeel("canvas-" + data.feelingid, -40, 51);
            } else if (sentiment == "good") {
                canvas.youfeel("canvas-" + data.feelingid, 40, 51);
            } else {
                canvas.youfeel("canvas-" + data.feelingid, 0, 51);
            }
        }

        function prepare_data(feeling, width) {
            feeling["root"] = root;
            feeling["width"] = width;
            feeling["height"] = width / 1.618;
            return feeling;
        }

        function render_counter(data, container) {
            if (last_counter == "" || last_feeling != last_counter) {
                create_counter(data, container);
            }
            update_counter(data);
        }

        function update_counter(data) {
            if (data.feeling_sentiment_value == "good") {
                $("#counter_good_" + last_counter).text(parseInt($("#counter_good_" + last_counter).text()) + 1);
            } else if (data.feeling_sentiment_value == "bad") {
                $("#counter_bad_" + last_counter).text(parseInt($("#counter_bad_" + last_counter).text()) + 1);
            } else {
                $("#counter_neutral_" + last_counter).text(parseInt($("#counter_neutral_" + last_counter).text()) + 1);
            }
        }

        function create_counter(data, container) {
            data.root = root;
            var element = counter(data);
            $(container).append(element);
            last_counter = data.feelingid;
            last_feeling = data.feelingid;
        }

        function reset() {
            last_counter = "";
            last_feeling = "";
        }

        return {
            render: render,
            reset: reset
        }
    });