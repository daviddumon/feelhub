require.config(
    {
        paths: {
            "jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min",
            "modules": "../modules",
            "plugins": "../plugins",
            "view": "../view",
            "templates": "../templates",
            "hogan": "../plugins/hogan",
            "text": "../plugins/text"
        }
    }
);

require(["plugins/domReady!", "modules/interface", "modules/bookmarklet"],

    function (doc, interface, bookmarklet) {
        interface.init();
        bookmarklet.search_topic_for();
    });