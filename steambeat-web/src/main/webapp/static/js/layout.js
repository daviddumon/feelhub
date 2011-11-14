/* Copyright bytedojo 2011 */
$(function() {
    $("#submitsearch").click(function(e) {
        var form = $("#formsearch").get(0);
        if(form.checkValidity()) {
            var uri = $("#searchstring").val();
            e.preventDefault();
            e.stopImmediatePropagation();
            alert("soon :)");
            //if(uri.length > 0) {
            //    location.href = $("#submitsearch").attr("action") + uri;
            //}
        }
    });

    $("#searchstring").focus(function() {
        if($(this).val() == "search") {
            $(this).val("");
        }
    });
});