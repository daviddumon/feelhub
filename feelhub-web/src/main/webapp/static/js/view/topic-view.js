define(["jquery", "plugins/hgn!templates/topic", "modules/canvas"],

    function ($, template, canvas) {

        varmax_ms_to_be_new = 21600000;

        $("body").on("DOMNodeInserted", "#flow li", function (event) {
            var last_inserted_topic = event.target;

            canvas.pie("pie-" + $(last_inserted_topic).attr("id"), {
                "happy": $(last_inserted_topic).data("happyFeelingCount"),
                "bored": $(last_inserted_topic).data("boredFeelingCount"),
                "sad": $(last_inserted_topic).data("sadFeelingCount")
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
            var happy = parseInt(data.happyFeelingCount);
            var sad = parseInt(data.sadFeelingCount);
            var bored = parseInt(data.boredFeelingCount);

            data.totalFeelingCount = happy + sad + bored;

            if (data.totalFeelingCount == 0) {
                data.totalFeelingText = "No feeling";
            } else if (data.totalFeelingCount == 1) {
                data.totalFeelingText = "1 feeling";
            } else {
                data.totalFeelingText = data.totalFeelingCount + " feelings";
            }

            if (happy > sad && happy > bored) {
                data.counterClass = "happy";
            } else if (sad > happy && sad > bored) {
                data.counterClass = "sad";
            } else {
                data.counterClass = "bored";
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