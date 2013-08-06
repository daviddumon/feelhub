require.config(
    {
        paths: {
            "jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min",
            "view": "../view",
            "modules": "../modules",
            "plugins": "../plugins",
            "templates": "../templates",
            "hogan": "../plugins/hogan",
            "text": "../plugins/text"
        }
    }
);

require(["plugins/domReady!", "modules/interface", "modules/authentification", "modules/flow", "view/flow/feeling-view"],

    function (doc, interface, authentification, flow, view) {
        interface.init();
        authentification.init();

        var list_feeling_api_end_point = root + "/api/feelings";
        flow.init(list_feeling_api_end_point, null, view, null);
    });