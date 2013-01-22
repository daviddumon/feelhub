require.config(
    {
        paths: {
            'jquery': 'https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min',
            'view': '../view',
            'modules': "../modules",
            "plugins": "../plugins",
            "templates": "../view/templates",
            "hogan": "../plugins/hogan",
            "text": "../plugins/text"
        }
    }
);

require(["plugins/domReady!", "modules/interface", "modules/flow", "view/flow/feeling-view"],

    function (doc, interface, flow, view) {
        var list_feeling_api_end_point = root + "/api/feelings";
        interface.init();
        flow.init(list_feeling_api_end_point, null, view, null);
    });