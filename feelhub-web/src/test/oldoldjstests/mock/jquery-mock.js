define([], function() {

    $.ajax = function(json) {
        $.ajaxcall = json;
    };

    return $;
});