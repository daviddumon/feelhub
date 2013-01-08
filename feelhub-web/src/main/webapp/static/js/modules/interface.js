define(['jquery'], function ($) {

    function init() {

        $("#logout").click(function () {
            $.ajax({
                url: root + '/sessions',
                type: 'DELETE',
                success: success,
                error: error
            });
        });
    }

    function success() {
        document.location.reload();
    }

    function error() {
        console.log("error while logging out.")
    }

    return {
        init: init
    };
});