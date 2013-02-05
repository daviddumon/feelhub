/* Copyright Feelhub 2012 */
require.config({
    paths: {
        'jquery': 'https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min',
        'view': '../view',
        'modules': "../modules",
        "plugins": "../plugins",
        "templates": "../view/templates",
        "hogan": "../plugins/hogan",
        "text": "../plugins/text"
    }
});

require(["jquery", "modules/interface", "modules/topic", "view/dashboard/dashboard-info", "modules/polling", "modules/carousel"],
    function ($, interface, topic, dashboard_info, polling, carousel) {
        carousel.init();
        interface.init();
        topic.init();
        dashboard_info.render(topicData);
        polling.RequestCounters();
        polling.RequestMedias();
        polling.RequestRelations();
    });