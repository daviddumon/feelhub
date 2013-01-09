define(["jquery", "plugins/hgn!templates/dashboard/dashboard_medias", "modules/carousel"],
    function ($, template, carousel) {

        var dashboard_container = "#dashboard";

        function render(data) {
            if (data.length > 0) {
                var element = template({"root": root, "medias": data});
                $(dashboard_container).append(element);
                carousel.compute();
            }
        }

        return  {
            render: render
        };
    });