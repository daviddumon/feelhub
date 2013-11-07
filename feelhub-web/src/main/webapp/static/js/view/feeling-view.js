define(["jquery", "plugins/hgn!templates/feeling", "modules/canvas"],

    function ($, template, canvas) {

        var texts_for_feelings = {
            good: [
                "likes this",
                "very likes this !",
                "very likes this !",
                "very likes this !",
                "thinks this is pure awesomeness !"
            ],
            neutral: [
                "doesn't care about this",
                "doesn't see the point of this",
                "doesn't see the point of this",
                "doesn't see the point of this",
                "does not give a shit about this !"
            ],
            bad: [
                "dislikes this",
                "really dislikes this !",
                "really dislikes this !",
                "really dislikes this !",
                "is very angry about this !"
            ]
        };

        function render(data, container) {
            render_feeling(data, container);
        }

        function render_feeling(data, container) {

            if (data.text[0] == "") {
                var text_values = texts_for_feelings[data.feelingValue];
                var index = parseInt(data.force) < text_values.length ? parseInt(data.force - 1) : text_values.length - 1;
                data.notext = "1 person " + text_values[index];
            }

            var element = template(data);
            $(container).append(element);
            canvas.feeling(data.feelingValue, "canvas-" + data.feelingid);
        }

        return {
            render: render
        }
    });