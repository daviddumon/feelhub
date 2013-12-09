define(["jquery"], function ($) {

    var addTopic = "#add-topic";

    function init() {
        $("#add-topic-submit").on("click", create);
    }

    function create(e) {
        e.preventDefault();
        e.stopImmediatePropagation();
        var anything = value();

        if (anything != "") {
            $.ajax({
                url: root + "/api/topics",
                type: "POST",
                data: {"name": anything},
                success: create_succes,
                error: error
            });
        }
        return false
    }

    function value() {
        return $(addTopic).find("input[type='text']").val();
    }

    function create_succes(data, textStatus, jqXHR) {
        redirect(jqXHR.getResponseHeader('Location'));
    }

    function error(jqXHR, textStatus, errorThrown) {
        if (jqXHR.status == 401) {
            $("body").trigger("needlogin");
        }
    }

    function redirect(value) {
        window.location.replace(value);
    }

    return {
        init: init
    }
});