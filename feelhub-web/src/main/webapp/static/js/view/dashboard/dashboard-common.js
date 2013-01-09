define(["jquery",
    "plugins/hgn!templates/dashboard/dashboard_main",
    "plugins/hgn!templates/dashboard/dashboard_optional",
    "modules/carousel"],
    function ($, main_template, optional_template, carousel) {

        var dashboard_container = "#dashboard";

        function render(topicData) {
            renderTemplate(main_template, topicData);
            if (hasOptionalData(topicData)) {
                renderTemplate(optional_template, topicData);
            }
        }

        function hasOptionalData(topicData) {
            return (topicData.description !== "" || topicData.uris.length > 0 || topicData.subTypes.length > 0);
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