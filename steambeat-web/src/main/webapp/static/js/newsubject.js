/* Copyright bytedojo 2011 */
var newsubject = {};

(function ($) {

    this.create = function (uri) {
        var postData = {
            uri:uri
        };
        var jqxhr = $.post(root + "/webpages", postData,
            function (data, text, xhr) {
                window.location = xhr.getResponseHeader("Location");
            }).error(function () {
                window.location = "/error";
            });
    };
}).call(newsubject, jQuery);