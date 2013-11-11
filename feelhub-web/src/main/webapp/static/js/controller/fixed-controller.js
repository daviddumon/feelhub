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
        },
        waitSeconds: 0
    }
);

require(["plugins/domReady!", "modules/interface"],

    function (doc, interface) {
        interface.init();
    });