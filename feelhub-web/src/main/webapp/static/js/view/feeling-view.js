define(["jquery", "plugins/hgn!templates/feeling", "modules/canvas"],

    function ($, template, canvas) {

        function render(data, container) {
            render_feeling(data, container);
        }

        function render_feeling(data, container) {
            var element = template(data);
            $(container).append(element);
            canvas.feeling(data.feelingValue, "canvas-" + data.feelingid);
        }

        return {
            render: render
        }
    });