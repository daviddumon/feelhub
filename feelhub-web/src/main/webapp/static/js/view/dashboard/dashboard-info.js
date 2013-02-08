define(["jquery",
    "plugins/hgn!templates/dashboard/dashboard_info",
    "modules/carousel"],
    function ($, template, carousel) {

        var dashboard_container = "#dashboard";

        function render(topicData) {
            $("#topic-name span").html(topicData.name);
            topicData.root = root;
            render_template(template, topicData);
        }

        function render_template(template, topicData) {
            var element = template(topicData);
            $(dashboard_container).append(element);
            carousel.compute();
        }

        return  {
            render: render
        };
    });