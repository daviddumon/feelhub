require.config(
    {
        paths: {
            'jquery': 'https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min',
            'modules': "../modules",
            "plugins": "../plugins"
        }
    }
);

require(["plugins/domReady!", "modules/interface", "modules/signup"], function (doc, interface, signup) {
    interface.init();
    signup.init();
});