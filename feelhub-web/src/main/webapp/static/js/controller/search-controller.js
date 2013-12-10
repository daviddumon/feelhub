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

require(["jquery", "plugins/domReady!", "modules/interface", "view/topic-view"],

    function ($, doc, feelhub, view) {
        feelhub.init();
        var container = $("#flow");
        var uri = root + "/api/topics/textsearch?q=" + query;
        $.getJSON(uri, function (data) {
            if (data.length > 0) {
                view.render_multiple(data, container);
            }
        });


    });