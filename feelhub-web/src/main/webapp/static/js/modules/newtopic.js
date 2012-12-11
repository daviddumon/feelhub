define(['plugins/domReady!','jquery'], function (doc,$) {

    var container = "#createtopic";
    var api_end_point = "/api/topics";

    function init() {
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
        document.location.href = jqXHR.getResponseHeader('Location');
    }

    function error() {
        console.log("newtopic ajax error");
    }

    return {
        init:init
    };
});