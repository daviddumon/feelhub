require.config(
    {
        paths:{
            'jquery':'https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min',
            'view':'../view/dashboard',
            'modules':"../modules",
            "plugins":"../plugins",
            "templates":"../view/templates",
            "hogan":"../plugins/hogan",
            "text":"../plugins/text"
        }
    }
);

require(["plugins/domReady!","jquery", "modules/interface", "modules/newtopic", "view/dashboard-view"], function (doc, $, interface, newtopic, view) {
    interface.init();
    newtopic.init();
    $('#dashboard, #command').wrapAll('<form id="createtopic">');
    view.render(topicData, true);
});