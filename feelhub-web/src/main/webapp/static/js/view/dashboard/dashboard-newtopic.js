define(["jquery",
    "plugins/hgn!templates/dashboard/dashboard_newtopic",
    "modules/carousel"],
    function ($, template, carousel) {

        var dashboard_container = "#dashboard";

        function render(topicData) {
            topicData.types = types;
            var element = template(topicData);
            $(dashboard_container).append(element);
            carousel.compute();
        }

        return {
            render: render
        }
    });