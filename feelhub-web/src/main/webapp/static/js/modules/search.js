define(['jquery', 'modules/flow', 'view/flow/topic-view', 'view/search/search-command-http-view', 'view/search/search-command-real-view'],

    function ($, flow, topicview, httpview, realview) {

    var api_end_point = root + "/api/topics";
    var command_container = "#command";

    function doSearch() {
        createCommand();
        flow.init(api_end_point, "q=" + q,topicview);
    }

    function createCommand() {
        if (authentificated) {
            if (type == 'http') {
                httpview.render(command_container);
            } else if (type == 'real') {
                realview.render(command_container);
            }
        }
    }

    return {
        doSearch: doSearch
    }
});