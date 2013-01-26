define(["jquery", "plugins/hgn!templates/dashboard/dashboard_medias", "modules/carousel"],
    function ($, template, carousel) {

        var container = "#dashboard";

        function render(data) {
            var main = data.shift();
            if (main.illustration == "") {
                main.illustration = root + "/static/images/unknown.png";
            }
            var element = template({"root": root, "medias": data, "illustration": main.illustration});
            $(container).append(element);
            carousel.compute();
        }

        return  {
            render: render
        };
    });