define(["jquery", "view/dashboard/dashboard-sentiment", "view/dashboard/dashboard-medias", "view/dashboard/dashboard-related"], function ($, sentiment_view, medias_view, related_view) {

    function RequestRelations() {
        $.getJSON(root + "/api/topic/" + topicData.id + "/related?limit=6", function (data) {
            related_view.render(data);
        });
    }

    function RequestCounters() {
        $.getJSON(root + "/api/topic/" + topicData.id + "/statistics?granularity=all&start=0&end=" + new Date().getTime(), function (data) {
            sentiment_view.render(data);
        });
    }

    function RequestMedias() {
        $.getJSON(root + "/api/topic/" + topicData.id + "/medias?limit=6", function (data) {
            medias_view.render(data);
        });
    }

    return {
        RequestRelations: RequestRelations,
        RequestCounters: RequestCounters,
        RequestMedias: RequestMedias,
    }
});
