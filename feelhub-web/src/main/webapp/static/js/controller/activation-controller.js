require.config(
    {
        paths: {
            'jquery': 'https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min',
            'modules': "../modules",
            "plugins": "../plugins"
        }
    }
);

require(["plugins/domReady!", "modules/interface"],

    function (doc, interface) {
        interface.init();
        setTimeout(function () {
            window.location.href = root + "/login";
        }, 2000);
    });