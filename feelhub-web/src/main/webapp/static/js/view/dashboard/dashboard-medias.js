define(["jquery", "plugins/hgn!templates/dashboard/dashboard_medias", "modules/carousel"],
    function ($, template, carousel) {

        var container = "#dashboard";

        function render(data) {
            var main = data.shift();
            if (main.thumbnail == "") {
                main.thumbnail = root + "/static/images/unknown.png";
            }
            var element = template({"root": root, "medias": data, "thumbnail": main.thumbnail, "type": main.type, "id": main.id});
            $(container).prepend(element);
            carousel.compute();
        }

        return  {
            render: render
        };
    });