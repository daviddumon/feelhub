define(["jquery", "plugins/hgn!templates/topic", "modules/canvas"],

    function ($, template, canvas) {

        function render(data, container) {
            data.root = root;
            data.totalFeelingCount = parseInt(data.goodFeelingCount) + parseInt(data.neutralFeelingCount) + parseInt(data.badFeelingCount);
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