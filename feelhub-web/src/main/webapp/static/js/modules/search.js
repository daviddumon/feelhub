define(['jquery'], function ($) {

    var api_end_point = "/api/topics";

    function doSearch() {

        $.ajax({
            url:root + api_end_point,
            type:'GET',
            data:JSON.stringify({"description":q}),
            success:success,
            error:error
        });

        function success(data, textStatus, jqXHR) {
            console.log("newtopic ajax success");
        }

        function error() {
            console.log("newtopic ajax error");
        }

        //$.getJSON(root + "/api/topics", {"description":q}, function (data) {
        //    console.log("success");
        //});
    }

    return {
        doSearch:doSearch
    }
});