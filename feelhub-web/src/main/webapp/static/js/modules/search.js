define(['jquery', 'modules/flow', 'view/flow/topic-view', 'view/search/search-command-http-view', 'view/search/search-command-real-view'],

    function ($, flow, topicview, httpview, realview) {

    var api_end_point = root + "/api/topics";
    var command_container = "#command";

    function doSearch() {
        flow.init(api_end_point, "q=" + q, topicview, createCommand);
    }

    function createCommand() {
        if (authentificated) {
            if (type == 'http') {
                if($("#flow li").length == 0) {
                    httpview.render(command_container);
                }
            } else if (type == 'real') {
                realview.render(command_container);
            }
        }
    }

    return {
        doSearch: doSearch
    }
});