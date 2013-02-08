define(["jquery", "modules/parser/sentiment-detector"], function ($, detector) {

    var uri = /((http|https):\/\/)?([%a-zA-Z_0-9\.-]+)(\.[a-z]{2,3}){1}(\/.*$)?/i;
    var httppunct = /[\[\!\"\$\'\(\)\*\+\,\\\;\<\>\=\]\^\{\}\|\~]/g;

    function analyze(text) {
        var results = [];
        var tokens = text.split(/\s/);
        $.each(tokens, function (index, token) {
            if (uri.test(token)) {
                var value = clean_http(token).toLowerCase();
                add(results, {"name": value, "sentiment": detector.get_sentiment(token, text, 5)});
            }
        });
        return results;
    }

    function clean_http(token) {
        return token.replace(httppunct, "");
    }

    function add(results, options) {
        options.sentiment = options.sentiment || "none";
        results.push({"name": options.name, "sentiment": options.sentiment});
    }

    return {
        analyze: analyze
    }
});