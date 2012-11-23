/* Copyright Feelhub 2012 */
$(function () {
    $("#logout").click(function () {
        $.ajax({
            url:root + '/sessions',
            type:'DELETE',
            success:function (data, status, jqXHR) {
                location.reload();
            }
        });
    });

    $("#search").submit(function(event) {
        var query = $("#search input").val();
        $(this).attr("action", root + "/search/" + encodeURIComponent(query));
    });
});