define(["jquery", "plugins/hgn!templates/flow/flow_feeling", "plugins/hgn!templates/flow/flow_counter_feeling", "plugins/hgn!templates/flow/flow_editable_feeling"],

    function ($, feeling, counter, editable) {

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
            if (userId == data.userId) {
                render_editable(data, container);
            } else {
                var element = feeling(prepare_data(data));
                $(container).append(element);
                last_feeling = data.feelingid;
            }
        }

        function render_editable(data, container) {
            var element = editable(prepare_data(data));
            $(container).append(element);
            last_feeling = data.feelingid;
        }

        function prepare_data(feeling) {
            feeling["realtypes"] = realtypes;
            feeling["root"] = root;
            feeling["height"] = (feeling.sentimentDatas.length != 0 ? 40 : 0) + 168 * (Math.floor(feeling.sentimentDatas.length / 2) + feeling.sentimentDatas.length % 2) + 'px';
            shuffleAndMakeFirstLarge(feeling.sentimentDatas);
            return feeling;
        }

        function shuffleAndMakeFirstLarge(datas) {
            for (var i = 0; i < datas.length; i++) {
                //attention hack degueu a enlever au plus vite
                //faut separer les traitements suivant si on a un topic dans le context ou pas
                if (typeof topicData.id != 'undefined') {
                    datas[i].original_index = i + 1;
                } else {
                    datas[i].original_index = i;
                }
            }

            $.each(datas, function (index, data) {
                data.classes = "topic_stack";
            });

            if (datas.length % 2 != 0) {
                var shuffle_number = Math.floor(Math.random() * datas.length);
                for (var i = 0; i < shuffle_number; i++) {
                    var rd = datas.shift();
                    datas.push(rd);
                }
                datas[0]["classes"] = " topic_large";
                datas[0].thumbnailMedium = datas[0].thumbnailLarge
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