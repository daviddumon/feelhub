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

require(["plugins/domReady!","jquery", "modules/interface", "modules/flow", "view/flow/feeling-view", "modules/form/newfeeling/new-feeling-form"],

    function (doc, $, interface, flow, view, new_feeling_form) {
        var list_feeling_api_end_point = root + "/api/myfeelings";
        interface.init();
        new_feeling_form.init();
        flow.init(list_feeling_api_end_point, null, view, null);
    });