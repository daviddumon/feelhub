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

require(["plugins/domReady!","jquery", "modules/interface", "modules/flow", "view/flow/feeling-view", "modules/newfeeling"],

    function (doc, $, interface, flow, view, newfeeling) {
        var list_feeling_api_end_point = root + "/api/myfeelings";
        interface.init();
        newfeeling.init();
        flow.init(list_feeling_api_end_point, null, view, null);
    });