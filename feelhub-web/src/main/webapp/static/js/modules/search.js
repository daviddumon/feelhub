define(['jquery', 'view/search/search-command-http-view', 'view/search/search-command-real-view'], function ($, httpview, realview) {

    var api_end_point = "/api/topics?q=" + q;
    var command_container = "#command";

    function doSearch() {

        $.ajax({
            url:root + api_end_point,
            type:'GET',
            success:success,
            error:error
        });

        function success(data, textStatus, jqXHR) {
            // update flow
            createCommand();
        }

        function error() {
            console.log("error" + api_end_point);
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
    }

    return {
        doSearch:doSearch
    }
});