require.config(
    {
        paths: {
            'jquery': 'https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min',
            'view': '../view',
            'modules': "../modules",
            "plugins": "../plugins",
            "templates": "../view/templates",
            "hogan": "../plugins/hogan",
            "text": "../plugins/text"
        }
    }
);

require(["plugins/domReady!", "jquery", "modules/interface", "modules/newtopic", "view/dashboard/dashboard-view"], function (doc, $, interface, newtopic, view) {
    interface.init();
    $('#dashboard, #command').wrapAll('<form id="createtopic">');
    newtopic.init();
    view.render(topicData, true);
});