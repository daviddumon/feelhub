define(["jquery", "plugins/hgn!templates/topic", "modules/canvas"],

    function ($, template, canvas) {

        var max_ms_to_be_new = 3600000;

        $("body").on("DOMNodeInserted", "#flow li", function (event) {
            var last_inserted_topic = event.target;

            canvas.pie("pie-" + $(last_inserted_topic).attr("id"), {
                "good": $(last_inserted_topic).data("goodFeelingCount"),
                "neutral": $(last_inserted_topic).data("neutralFeelingCount"),
                "bad": $(last_inserted_topic).data("badFeelingCount")
            });
        });

        function render(data, container) {
            var element = getElement(data);
            $(container).append(element);
        }

        function render_multiple(data, container) {
            var elements = [];

            $.each(data, function (index, data) {
                var element = getElement(data);
                elements.push(element);
            });

            $(container).append(elements);
        }

        function getElement(data) {
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

            if ((new Date() - data.creationDate) <= max_ms_to_be_new) {
                data.new = true;
            }

            return template(data);
        }

        return {
            render: render,
            render_multiple: render_multiple
        }
    });