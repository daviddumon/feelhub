/* Copyright bytedojo 2011 */
var newfeed = {};

(function($) {

    this.create = function(uri) {
        var postData  = {
            uri : uri
        };
        $.post(root + "/feeds", postData, function(data, text, xhr) {
            $.redirect(xhr.getResponseHeader("Location"));
        });
    };
}).call(newfeed, jQuery);