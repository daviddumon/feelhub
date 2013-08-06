/* Copyright Feelhub 2012 */
require.config({
    paths: {
        "jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min",
        "view": "../view",
        "modules": "../modules",
        "plugins": "../plugins",
        "templates": "../templates",
        "hogan": "../plugins/hogan",
        "text": "../plugins/text"
    }
});

//require(["plugins/domReady!", "jquery", "modules/interface", "modules/flow", "view/flow/feeling-view", "modules/form/newfeeling/new-feeling-form", "modules/canvas"],
require(["plugins/domReady!", "jquery", "modules/interface", "modules/authentification", "modules/canvas"],

    //function (doc, $, interface, flow, feeling_view, form_new_feeling, canvas) {
    function (doc, $, interface, authentification, canvas) {
        interface.init();
        authentification.init();
        //form_new_feeling.init();
        //flow.init(root + "/api/topic/" + topicData.id + "/feelings", null, feeling_view, null);
        //canvas.youfeel("canvas-sentiment", topicData.topicSentimentScore, 82);
        canvas.youfeel("canvas-youfeel", null, 82);
        canvas.youfeel("canvas-theyfeel", -80, 82);
    });