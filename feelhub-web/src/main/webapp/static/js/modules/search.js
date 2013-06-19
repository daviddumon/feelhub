define(["jquery", "modules/flow", "view/flow/topic-view", "view/search/search-command-http-view", "view/search/search-command-real-view"],

    function ($, flow, topicview, httpview, realview) {

    var api_end_point = root + "/api/topics";
    var command_container = "#command";

    function do_search() {
        flow.init(api_end_point, q, topicview, create_command);
    }

    function create_command() {
        //if (authentificated) {
        //    if (type == "http") {
        //        if($("#flow li").length == 0) {
        //            httpview.render(command_container);
        //        }
        //    } else if (type == "real") {
        //        realview.render(command_container);
        //    }
        //}
    }

    return {
        do_search: do_search
    }
});