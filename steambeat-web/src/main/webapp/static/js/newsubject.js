/* Copyright bytedojo 2011 */
var newsubject = {};

(function ($) {

    this.create = function (subjectId) {
        var postData = {
            uri:subjectId
        };
        $.post(root + "/webpages", postData, function (data, text, xhr) {
            window.location = xhr.getResponseHeader("Location");
        });
    };
}).call(newwebpage, jQuery);