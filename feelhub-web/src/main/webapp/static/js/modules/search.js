define(['jquery', 'view/search/search-command-web-view', 'view/search/search-command-real-view'], function ($, webview, realview) {

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
            // update flow
            createCommand();
        }

        function createCommand() {
            if (authentificated) {
                if (type == 'web') {
                    webview.render(command_container);
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