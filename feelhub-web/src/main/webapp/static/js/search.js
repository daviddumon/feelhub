$(function() {
    console.log("let's do search");
    $.getJSON(root + "/api/search?q=" + q, function(data) {
        console.log("search results");
        console.log(data);
    });
});