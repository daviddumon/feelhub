require.config(
    {
        paths: {
            "jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min",
            "view": "../view",
            "modules": "../modules",
            "plugins": "../plugins",
            "templates": "../templates",
            "hogan": "../plugins/hogan",
            "text": "../plugins/text"
        }
    }
);

require(["plugins/domReady!", "jquery", "modules/interface", "modules/newtopic", "view/dashboard/dashboard-newtopic"],

    function (doc, $, interface, newtopic, view) {
        interface.init();
        $("#carousel-wrapper, #command").wrapAll("<form id='createtopic'>");
        newtopic.init();
        view.render(topicData);
    });