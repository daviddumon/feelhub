define(["jquery", "plugins/hgn!templates/feelings/feeling", "modules/canvas"],

    function ($, template, canvas) {

        function render(data, container) {
            render_feeling(data, container);
        }

        function render_feeling(data, container) {
            var element = template(data);
            $(container).append(element);

            var feelingValue = data.feelingValue;
            if (feelingValue == "bad") {
                canvas.youfeel("canvas-" + data.feelingid, -40);
            } else if (feelingValue == "good") {
                canvas.youfeel("canvas-" + data.feelingid, 40);
            } else {
                canvas.youfeel("canvas-" + data.feelingid, 0);
            }
        }

        return {
            render: render
        }
    });