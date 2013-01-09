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

require(["jquery", "modules/interface", "modules/topic", "view/dashboard/dashboard-common", "modules/flow", "modules/polling", "modules/carousel"],
    function ($, interface, topic, dashboard_common, flow, polling, carousel) {
        carousel.init();
        interface.init();
        topic.init();
        dashboard_common.render(topicData);
        flow.init();
        polling.RequestCounters();
        polling.RequestMedias();
        polling.RequestRelations();
    });