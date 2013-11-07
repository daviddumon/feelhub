define(["jquery", "plugins/hgn!templates/topic", "modules/canvas"],

    function ($, template, canvas) {

        $("body").on("DOMNodeInserted", "#flow", function (event) {
            var last_inserted_topic = event.target;

            canvas.pie("pie-" + $(last_inserted_topic).data("id"), {
                "good": $(last_inserted_topic).data("goodFeelingCount"),
                "neutral": $(last_inserted_topic).data("neutralFeelingCount"),
                "bad": $(last_inserted_topic).data("badFeelingCount")
            });
        });

        function render(data, container) {
            data.root = root;
            var good = parseInt(data.goodFeelingCount);
            var bad = parseInt(data.badFeelingCount);
            var neutral = parseInt(data.neutralFeelingCount);

            data.totalFeelingCount = good + bad + neutral;

            if (data.totalFeelingCount == 0) {
                data.totalFeelingText = "No feeling";
            } else if (data.totalFeelingCount == 1) {
                data.totalFeelingText = "1 feeling";
            } else {
                data.totalFeelingText = data.totalFeelingCount + " feelings";
            }

            if (good > bad && good > neutral) {
                data.counterClass = "good";
            } else if (bad > good && bad > neutral) {
                data.counterClass = "bad";
            } else {
                data.counterClass = "neutral";
            }

            if (data.thumbnail == "") {
                data.thumbnail = root + "/static/images/unknown.png";
            }

            var element = template(data);

            $(container).append(element);
        }

        function render_multiple(data, container) {

            var elements = [];

            $.each(data, function (index, data) {
                data.root = root;
                var good = parseInt(data.goodFeelingCount);
                var bad = parseInt(data.badFeelingCount);
                var neutral = parseInt(data.neutralFeelingCount);

                data.totalFeelingCount = good + bad + neutral;

                if (data.totalFeelingCount == 0) {
                    data.totalFeelingText = "No feeling";
                } else if (data.totalFeelingCount == 1) {
                    data.totalFeelingText = "1 feeling";
                } else {
                    data.totalFeelingText = data.totalFeelingCount + " feelings";
                }

                if (good > bad && good > neutral) {
                    data.counterClass = "good";
                } else if (bad > good && bad > neutral) {
                    data.counterClass = "bad";
                } else {
                    data.counterClass = "neutral";
                }

                if (data.thumbnail == "") {
                    data.thumbnail = root + "/static/images/unknown.png";
                }

                var element = template(data);
                elements.push(element);
            });

            $(container).append(elements);
        }

        return {
            render: render,
            render_multiple: render_multiple
        }
    });