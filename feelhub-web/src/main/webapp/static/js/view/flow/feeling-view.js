define(["jquery", "plugins/hgn!templates/flow/flow_feeling", "plugins/hgn!templates/flow/flow_empty_feeling"],

    function ($, feeling, counter) {

        var last_feeling = "";
        var last_counter = "";

        function render(data, container) {
            if (data.text.length == 1 && data.text[0] == "") {
                render_counter(data, container);
            } else {
                render_feeling(data, container);
            }
        }

        function render_feeling(data, container) {
            data.root = root;
            data.realtypes = realtypes;
            var element = feeling(prepare_data(data));
            $(container).append(element);
            last_feeling = data.id;
        }

        function prepare_data(feeling) {
            feeling["root"] = root;
            feeling["height"] = (feeling.topicDatas.length != 0 ? 40 : 0) + 146 * (Math.floor(feeling.topicDatas.length / 2) + feeling.topicDatas.length % 2) + 'px';
            shuffleAndMakeFirstLarge(feeling.topicDatas);
            return feeling;
        }

        function shuffleAndMakeFirstLarge(datas) {
            if (datas.length % 2 != 0) {
                var shuffle_number = Math.floor(Math.random() * datas.length);
                for (var i = 0; i < shuffle_number; i++) {
                    var rd = datas.shift();
                    datas.push(rd);
                }
                datas[0]["classes"] = "topic_large";
            }
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
            last_counter = data.id;
            last_feeling = data.id;
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