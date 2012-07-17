/* Copyright Steambeat 2012 */
$(function () {
    $("#logout").click(function () {
        $.ajax({
            url:root + '/sessions',
            type:'DELETE',
            success:function(data, status, jqXHR) {
                document.location.href = jqXHR.getResponseHeader("Location");
            }
        });
    });
});