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
            happy: [
                "is happy about this",
                "is very happy about this!",
                "is very happy about this!",
                "is very happy about this!",
                "thinks this is pure awesomeness and brings joy to the world!"
            ],
            bored: [
                "is bored by this",
                "doesn't see the point of this",
                "doesn't see the point of this",
                "doesn't see the point of this",
                "does not give a shit about this !"
            ],
            sad: [
                "is sad about this",
                "is very sad about this!",
                "is very sad about this!",
                "is very sad about this!",
                "is very sad about this!",
            ]
        };

        function render(data, container, prepend) {
            if($("#topic-container").hasClass("nofeelings")) {
                $("#nofeelings").slideUp(800, function() {
                    $("#topic-container").removeClass("nofeelings");
                    $("#topic-container").addClass("feelings");
                });
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