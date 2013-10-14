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

require(["plugins/domReady!", "jquery", "modules/interface", "modules/authentification", "modules/canvas", "modules/form/feeling", "modules/flow-feelings"],

    function (doc, $, interface, authentification, canvas, form, flow) {
        interface.init();
        authentification.init();
        form.init();
        flow.init();
        canvas.pie("pie");
        canvas.feeling("good", "feeling-value-good");
        canvas.feeling("neutral", "feeling-value-neutral");
        canvas.feeling("bad", "feeling-value-bad");
    });