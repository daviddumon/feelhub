require.config(
    {
        paths: {
            "jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min",
            "modules": "../modules",
            "plugins": "../plugins",
            "view": "../view",
            "templates": "../templates",
            "hogan": "../plugins/hogan",
            "text": "../plugins/text"
        }
    }
);

require(["plugins/domReady!", "modules/interface", "modules/search"],

    function (doc, interface, search) {
        interface.init();
        search.do_search();
    });