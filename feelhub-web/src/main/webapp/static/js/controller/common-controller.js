require.config(
    {
        paths: {
            "jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min",
            "modules": "../modules",
            "plugins": "../plugins"
        }
    }
);

require(["plugins/domReady!", "modules/interface"],

    function (doc, interface) {
        interface.init();
        $(".fixed-message").css("top", $(window).height() / 2 - $(".fixed-message").height() / 2);
        $(".fixed-message").show();
    });