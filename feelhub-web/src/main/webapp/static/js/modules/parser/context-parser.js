define(["jquery", "modules/parser/sentiment-detector"], function ($, detector) {

    var punct = /[\[\'\!\"\$\%\&\(\)\*\+\,\\\-\.\/\:\;\<\>\=\?\=\]\^\_\{\}\|\~]/g;

    function analyze(text, context) {
        var results = [];
        var tokens = text.split(/\s/);
        $.each(tokens, function (index, token) {
            handle_token(token, results, context, text);
        });
        return results;
    }

    function handle_token(token, results, context, text) {
        var tokens = token.split(punct);
        $.each(tokens, function (index, token) {
            var clean_token = clean(token).toLowerCase();
            if (clean_token.length > 1) {
                find_context_tags(results, clean_token, token, context, text);
            }
        });
    }

    function clean(token) {
        return token.replace(punct, "");
    }

    function find_context_tags(results, clean_token, token, context, text) {
        $.each(context, function (index, element) {
            var element_words = element.value.split(/\s/);
            $.each(element_words, function (index, word) {
                if (word === clean_token) {
                    add(results, {"name": element.value, "id": element.id, "thumbnail": element.thumbnail, "sentiment": detector.get_sentiment(token, text, 5)});
                }
            });
        });
    }

    function add(results, options) {
        if (options.name.indexOf("'") >= 0) {
            options.name = options.name.substring(options.name.indexOf("'") + 1);
        }
        options.thumbnail = options.thumbnail || "";
        results.push({"name": options.name, "sentiment": options.sentiment, "id": options.id, "thumbnail": options.thumbnail});
    }

    return {
        analyze: analyze
    }
});