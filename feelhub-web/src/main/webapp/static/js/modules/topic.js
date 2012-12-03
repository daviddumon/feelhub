define(['jquery'], function ($) {

    var container = "#feeling_form";
    var api_end_point = "/api/feelings";

    function init() {
        $(container).children().focus(function () {
            $("#form textarea").height("100px");
        });

        $(container).submit(function (event) {
            $.ajax({
                url:root + api_end_point,
                type:'POST',
                data:$(container).serialize(),
                success:success,
                error:error
            });
            return false;
        });
    }

    function success(data, textStatus, jqXHR) {
        $("#form textarea").val('');
        $("#form textarea").height("30px");
        if (topicId === "") {
            pollForId(data.id, text, sentimentValue);
        } else {
            flow.pushFake(data.id, text, sentimentValue);
        }
    }

    function error() {

    }

    return {
        init:init
    };
});