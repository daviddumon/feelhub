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
    },
    waitSeconds: 0
});

require(["plugins/domReady!", "jquery", "modules/interface", "modules/canvas", "modules/form/feeling", "modules/flow-feelings", "modules/polling"],

    function (doc, $, interface, canvas, form, flow, polling) {
        canvas.pie("pie");
        canvas.feeling("happy", "feeling-value-happy");
        canvas.feeling("bored", "feeling-value-bored");
        canvas.feeling("sad", "feeling-value-sad");
        interface.init();
        form.init();
    });