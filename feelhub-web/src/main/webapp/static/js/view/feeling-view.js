define(["jquery", "plugins/hgn!templates/feeling", "modules/canvas"],

    function ($, template, canvas) {

        $("body").on("DOMNodeInserted", "#feelings", function (event) {
            var last_feeling = event.target;
            canvas.feeling($(last_feeling).data("value"), "canvas-" + $(last_feeling).attr("id"));
            if ($(last_feeling).is(":hidden")) {
                $(last_feeling).slideDown(600);
            }
        });

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

        function render(data, container, prepend) {
            if($("#nofeelings").is(":visible")) {
                $("#nofeelings").slideUp(800);
            }

            var element = getElement(data);
            if(prepend) {
                data.class = "fake";
                $(container).prepend(element);
            } else {
                $(container).append(element);
            }
        }

        function getElement(data) {
            if (data.text[0] == "") {
                var text_values = texts_for_feelings[data.feelingValue];
                var index = parseInt(data.force) < text_values.length ? parseInt(data.force - 1) : text_values.length - 1;
                data.notext = "1 person " + text_values[index];
            }

            return template(data);
        }

        return {
            render: render
        }
    });