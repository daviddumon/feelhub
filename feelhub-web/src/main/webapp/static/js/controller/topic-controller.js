/* Copyright Feelhub 2012 */
require.config({
    paths: {
        "jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min",
        "view": "../view",
        "modules": "../modules",
        "plugins": "../plugins",
        "templates": "../templates",
        "hogan": "../plugins/hogan",
        "text": "../plugins/text"
    }
});

require(["plugins/domReady!", "jquery", "modules/interface", "modules/flow", "view/dashboard/dashboard-info", "modules/polling", "modules/carousel", "view/flow/feeling-view", "modules/form/newfeeling/new-feeling-form"],

    function (doc, $, interface, flow, dashboard_info, polling, carousel, feeling_view, form_new_feeling) {
        carousel.init();
        interface.init();
        dashboard_info.render(topicData);
        form_new_feeling.init();
        flow.init(root + "/api/topic/" + topicData.id + "/feelings", null, feeling_view, null);
        polling.request_counters();
        polling.request_medias();
        polling.request_relations();
    });