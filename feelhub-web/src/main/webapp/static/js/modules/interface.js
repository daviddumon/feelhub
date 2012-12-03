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

        $("#search").submit(function (event) {
            event.preventDefault();
            event.stopImmediatePropagation();
            var query = $("#search input").val();
            window.location.href = root + "/search/" + encodeURIComponent(query);
        });
    }

    return {
        init:init
    };
});