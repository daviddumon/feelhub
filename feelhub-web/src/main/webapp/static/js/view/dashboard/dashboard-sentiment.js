define(["jquery", "plugins/hgn!templates/dashboard/dashboard_sentiment", "modules/carousel"],
    function ($, template, carousel) {

        var dashboard_container = "#dashboard";

        function render(stat) {
            var element = template({"root": root});
            $(dashboard_container).append(element);
            carousel.compute();
        }

        return  {
            render: render
        };
    });