define(["jquery", "plugins/hgn!templates/dashboard/dashboard_medias", "modules/carousel"],
    function ($, template, carousel) {

        var container = "#dashboard-main";

        function render(data) {
            var main = data.shift();
            var element = template({"root": root, "medias": data,"illustration":main.illustration});
            $(container).append(element);
            carousel.compute();
        }

        return  {
            render: render
        };
    });