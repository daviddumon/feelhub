define(["jquery", "modules/parser/sentiment-detector"], function ($, detector) {

    var punct = /[\[\!\"\$\%\&\(\)\*\+\,\\\-\.\/\:\;\<\>\=\?\=\]\^\_\{\}\|\~]/g;

    function analyze(text) {
        var results = [];
        var tokens = text.split(/\s/);
        $.each(tokens, function (index, token) {
            handle_token(token, results, text);
        });
        return results;
    }

    function handle_token(token, results, text) {
        var elements = token.split(punct);
        $.each(elements, function (index, element) {
            var clean_token = clean(element).toLowerCase();
            if (clean_token.charAt(0) === '#') {
                add(results, {"name": clean_token.substring(1), "sentiment": detector.get_sentiment(element, text, 5)});
            }
        });
    }

    function clean(token) {
        return token.replace(punct, "");
    }

    function add(results, options) {
        if (options.name.indexOf("'") >= 0) {
            options.name = options.name.substring(options.name.indexOf("'") + 1);
        }
        options.sentiment = options.sentiment || "none";
        results.push({"name": options.name, "sentiment": options.sentiment});
    }

    return {
        analyze: analyze
    }
});