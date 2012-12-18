define(['jquery'], function ($) {

    var container = "#feeling_form";
    var api_end_point = "/api/feelings";

    function init() {

        $(container).children().focus(function () {
            $("#feeling_form textarea").height("100px");
        });

        $(container).submit(function () {
            $.ajax({
                url: root + api_end_point,
                type: 'POST',
                data: $(container).serialize(),
                success: success,
                error: error
            });
            return false;
        });
    }

    function success() {
        document.location.reload(true);
    }

    function error() {
        console.log("error while posting feeling.");
    }

    return {
        init: init
    };
});