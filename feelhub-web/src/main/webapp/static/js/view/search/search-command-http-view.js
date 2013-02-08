define(["jquery", "plugins/hgn!templates/search/search_command_http_view"],
    function ($, template) {

        var id = "create_http_topic";
        var api_end_point = "/api/topics";

        function render(container) {
            var search_command_web_view = template({"id": id});
            $(container).append(search_command_web_view);
            bind_behavior();
        }

        function bind_behavior() {
            $("#" + id).click(function (event) {
                event.preventDefault();
                event.stopImmediatePropagation();
                create_new_http_topic();
            });
        }

        function create_new_http_topic() {
            $.ajax({
                url: root + api_end_point,
                type: "POST",
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