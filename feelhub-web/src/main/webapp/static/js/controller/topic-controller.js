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

require(["plugins/domReady!", "jquery", "modules/interface", "modules/authentification", "modules/canvas", "modules/form/feeling"],

    function (doc, $, interface, authentification, canvas, form) {
        interface.init();
        authentification.init();
        form.init();
        //flow.init(root + "/api/topic/" + topicData.id + "/feelings", null, feeling_view, null);
        canvas.pie([]);
        canvas.youfeel("feeling-value-good", 60);
        canvas.youfeel("feeling-value-neutral", 0);
        canvas.youfeel("feeling-value-bad", -60);
    });