define(["jquery", "plugins/hgn!templates/dashboard/dashboard_related", "modules/carousel"],
    function ($, template, carousel) {

        var dashboard_container = "#dashboard";

        function render(data) {
            if (data.length > 0) {
                $.each(data, function (index, element) {
                    if (element.illustration == "") {
                        element.illustration = root + "/static/images/unknown.png";
                    }
                });
                var element = template({"root": root, "related": data});
                $(dashboard_container).append(element);
                carousel.compute();
            }
        }

        return  {
            render: render
        };
    });