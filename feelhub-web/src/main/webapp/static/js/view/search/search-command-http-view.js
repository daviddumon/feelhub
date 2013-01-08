define(['jquery', 'plugins/hgn!templates/search_command_http_view'],
    function ($, template) {

        var id = "create_http_topic";
        var api_end_point = "/api/topics/http";

        function render(container) {
            var search_command_web_view = template({"id": id});
            $(container).append(search_command_web_view);
            bindBehavior();
        }

        function bindBehavior() {
            $("#" + id).click(function (event) {
                event.preventDefault();
                event.stopImmediatePropagation();
                createNewHttpTopic();
            });
        }

        function createNewHttpTopic() {
            $.ajax({
                url: root + api_end_point,
                type: 'POST',
                data: {"q": q},
                success: success,
                error: error
            });

            function success(data, textStatus, jqXHR) {
                // update flow
                createCommand();
            }

            function error() {
                console.log("error : " + api_end_point);
            }
        }

        function success(data, textStatus, jqXHR) {
            document.location.reload();
        }

        function error() {
            console.log("error");
        }

        return {
            render: render
        }
    });