define(["jquery"],

    function ($) {

        function search_topic_for() {
            $.ajax({
                url: root + "/api/topics?q=" + uri,
                type: "GET",
                success: handle,
                error: error
            });
        }

        function handle(data) {
            if (data.length > 0) {
                redirect(root + "/topic/" + data[0].id);
            } else {
                create();
            }
        }

        function create() {
            $.ajax({
                url: root + "/api/topics",
                type: "POST",
                data: {"name": uri},
                success: create_succes,
                error: error
            });
        }

        function create_succes(data, textStatus, jqXHR) {
            redirect(jqXHR.getResponseHeader('Location'));
        }

        function error(jqXHR, textStatus,errorThrown) {
            console.log("error");
            if(jqXHR.status == 401) {
                redirect(root + "/login");
            }
        }

        function redirect(value) {
            window.location.replace(value);
        }

        return {
            search_topic_for: search_topic_for,
            handle: handle
        }
    });