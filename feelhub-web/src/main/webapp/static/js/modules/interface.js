define(['jquery'], function ($) {

    function init() {

        $("#logout").click(function () {
            $.ajax({
                url:root + '/sessions',
                type:'DELETE',
                success:function (data, status, jqXHR) {
                    location.reload();
                }
            });
        });
    }

    return {
        init:init
    };
});