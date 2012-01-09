/* Copyright bytedojo 2011 */
var newwebpage = {};

(function ($) {

    this.create = function (uri) {
        var postData = {
            uri:uri
        };
        $.post(root + "/webpages", postData, function (data, text, xhr) {
            window.location = xhr.getResponseHeader("Location");
        });
    };
}).call(newwebpage, jQuery);