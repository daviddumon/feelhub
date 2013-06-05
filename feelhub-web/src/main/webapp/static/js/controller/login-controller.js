require.config(
    {
        paths: {
            "jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min",
            "view": "../view",
            "modules": "../modules",
            "plugins": "../plugins",
            "templates": "../templates",
            "hogan": "../plugins/hogan",
            "text": "../plugins/text"
        }
    }
);

require(["plugins/domReady!", "modules/interface", "modules/login"],

    function (doc, interface, login) {
        interface.init();
        login.init();
    });