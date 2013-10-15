define(["jquery", "plugins/hgn!templates/topic", "modules/canvas"],

    function ($, template, canvas) {

        function render(data, container) {
            data.root = root;
            var good = parseInt(data.goodFeelingCount);
            var bad = parseInt(data.badFeelingCount);
            var neutral = parseInt(data.neutralFeelingCount);

            data.totalFeelingCount = good + bad + neutral;

            if(good > bad && good > neutral) {
                data.counterClass = "good";
            } else if(bad > good && bad > neutral ) {
                data.counterClass = "bad";
            } else {
                data.counterClass = "neutral";
            }

            if (data.thumbnail == "") {
                data.thumbnail = root + "/static/images/unknown.png";
            }

            var element = template(data);
            $(container).append(element);
            canvas.pie("pie-" + data.id, {
                "good": data.goodFeelingCount,
                "neutral": data.neutralFeelingCount,
                "bad": data.badFeelingCount
            });
        }

        return {
            render: render
        }
    });