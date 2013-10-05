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

require(["plugins/domReady!", "modules/interface", "modules/authentification", "modules/flow-topics"],

    function (doc, interface, authentification, flow, view) {
        interface.init();
        authentification.init();
        flow.init();
    });