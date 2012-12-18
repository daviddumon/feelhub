/* Copyright Feelhub 2012 */
require.config({
    paths: {
        'jquery': 'https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min',
        'view': '../view',
        'modules': "../modules",
        "plugins": "../plugins",
        "templates": "../view/templates",
        "hogan": "../plugins/hogan",
        "text": "../plugins/text"
    }
});

require(["jquery", "modules/interface", "modules/topic", "view/dashboard/dashboard-view", "modules/flow", "modules/polling"],
    function ($, interface, topic, dashboard, flow, polling) {
        interface.init();
        topic.init();
        dashboard.render(topicData, false);
        flow.init();

        if (topicData.id !== "") {
            $("#related").show();
            $("#" + topicData.id + " img").attr("src", illustrationLink);
            polling.RequestRelations(topicData.id);
            polling.RequestCounters(topicData.id);
        }
    });