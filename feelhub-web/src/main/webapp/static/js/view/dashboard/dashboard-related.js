define(["jquery", "plugins/hgn!templates/dashboard/dashboard_related", "modules/carousel"],
    function ($, template, carousel) {

        var dashboard_container = "#dashboard";

        function render(data) {
            var element = template({"root": root, "related": data});
            $(dashboard_container).append(element);
            carousel.compute();
        }

        return  {
            render: render
        };
    });