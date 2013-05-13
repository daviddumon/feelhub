/* Copyright Feelhub 2012 */
require.config({
    paths: {
        "jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min",
        "view": "../view",
        "modules": "../modules",
        "plugins": "../plugins",
        "templates": "../templates",
        "hogan": "../plugins/hogan",
        "text": "../plugins/text"
    }
});

require(["plugins/domReady!", "jquery", "modules/interface", "modules/flow", "view/flow/feeling-view", "modules/form/newfeeling/new-feeling-form", "modules/canvas"],

    function (doc, $, interface, flow, feeling_view, form_new_feeling, canvas) {
        interface.init();
        form_new_feeling.init();
        flow.init(root + "/api/topic/" + topicData.id + "/feelings", null, feeling_view, null);
        canvas.draw("canvas-sentiment", topicData.topicSentimentScore, 4);
    });