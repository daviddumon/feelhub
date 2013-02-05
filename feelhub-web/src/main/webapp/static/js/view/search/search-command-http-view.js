define(['jquery', 'plugins/hgn!templates/search/search_command_http_view'],
    function ($, template) {

        var id = "create_http_topic";
        var api_end_point = "/api/topics";

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
                data: {"name": q},
                success: success,
                error: error
            });
        }

        function success() {
            document.location.reload();
        }

        function error() {

        }

        return {
            render: render
        }
    });