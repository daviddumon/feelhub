require.config(
    {
        paths:{
            'jquery':'https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min',
            'modules':"../modules",
            "plugins":"../plugins"
        }
    }
);

require(["plugins/domReady!","modules/interface","modules/search"], function (doc, interface, search) {
    interface.init();
    search.doSearch();
});