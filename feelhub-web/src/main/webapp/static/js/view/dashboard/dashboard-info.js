define(["jquery",
    "plugins/hgn!templates/dashboard/dashboard_info",
    "modules/carousel"],
    function ($, template, carousel) {

        var dashboard_container = "#dashboard";

        function render(topicData) {
            $("#topic-name").html(topicData.name);
            renderTemplate(template, topicData);
        }

        function renderTemplate(template, topicData) {
            var element = template(topicData);
            $(dashboard_container).append(element);
            carousel.compute();
        }

        return  {
            render: render
        };
    });