define(["jquery", "plugins/hgn!templates/dashboard/dashboard_sentiment", "modules/carousel", "modules/canvas"],
    function ($, template, carousel, canvas) {

        var dashboard_container = "#dashboard";

        function render(data) {
            var element = template({"root": root});
            $(dashboard_container).append(element);
            carousel.compute();
            canvas.draw("canvas-sentiment", topicData.topicSentimentScore, 8);
        }

        return  {
            render: render
        };
    });