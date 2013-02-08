define(["jquery", "view/dashboard/dashboard-sentiment", "view/dashboard/dashboard-medias", "view/dashboard/dashboard-related"], function ($, sentiment_view, medias_view, related_view) {

    function request_relations() {
        $.getJSON(root + "/api/topic/" + topicData.id + "/related?limit=12", function (data) {
            if(data.length  > 0) {
                related_view.render(data);
            }
        });
    }

    function request_counters() {
        $.getJSON(root + "/api/topic/" + topicData.id + "/statistics?granularity=all&start=0&end=" + new Date().getTime(), function (data) {
            sentiment_view.render(data);
        });
    }

    function request_medias() {
        $.getJSON(root + "/api/topic/" + topicData.id + "/medias?limit=4", function (data) {
            if(data.length  > 0) {
                medias_view.render(data);
            }
        });
    }

    return {
        request_relations: request_relations,
        request_counters: request_counters,
        request_medias: request_medias,
    }
});
