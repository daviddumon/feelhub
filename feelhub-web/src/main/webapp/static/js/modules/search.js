define(['jquery'], function ($) {

    function doSearch() {

        $.getJSON(root + "/api/search?q=" + q, function (data) {
            console.log("search results");
            console.log(data);
        });
    }

    return {
        doSearch:doSearch
    }

});