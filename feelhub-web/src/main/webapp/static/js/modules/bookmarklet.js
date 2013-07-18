define(["jquery"],

    function ($) {

        function search_topic_for() {
            if (uri != "") {
                $.ajax({
                    url: root + "/api/topics?q=" + uri,
                    type: "GET",
                    success: handle,
                    error: error
                });
            }
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

        function error(jqXHR, textStatus, errorThrown) {
            if (jqXHR.status == 401) {
                $("#bookmarklet").hide();
                $("body").trigger("needlogin");
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