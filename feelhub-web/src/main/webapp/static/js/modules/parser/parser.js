define(["jquery", "modules/parser/uri-parser", "modules/parser/hashtag-parser", "modules/parser/context-parser"], function ($, uri_parser, hashtag_parser, context_parser) {

    var context = [];

    if (typeof topicData.id != "undefined") {
        $.getJSON(root + "/api/topic/" + topicData.id + "/context", function (data) {
            set_context(data);
        });
    }

    function analyze(text) {
        var results = [];
        parse_uris(text, results);
        parse_hashtags(text, results);
        parse_context(text, results);
        return results;
    }

    function parse_uris(text, results) {
        var uri_results = uri_parser.analyze(text);
        $.each(uri_results, function (index, result) {
            add(results, result);
        });
    }

    function parse_hashtags(text, results) {
        var hashtag_results = hashtag_parser.analyze(text);
        $.each(hashtag_results, function (index, result) {
            add(results, result);
        });
    }

    function parse_context(text, results) {
        var context_results = context_parser.analyze(text, context);
        $.each(context_results, function (index, result) {
            add(results, result);
        });
    }

    function add(results, result) {
        result.id = result.id || "";
        result.thumbnailSmall = result.thumbnailSmall || "";
        if (check_name(result.name, results)) {
            results.push({"name": result.name, "sentiment": result.sentiment, "id": result.id, "thumbnailSmall": result.thumbnailSmall});
        }
    }

    function check_name(name, results) {
        if (name !== "") {
            return !is_in_results(name, results);
        } else {
            return false;
        }
    }

    function is_in_results(name, results) {
        var present = false;
        $.each(results, function (index, data) {
            if (data.name === name) {
                present = true;
            }
        });
        return present;
    }

    function set_context(new_context) {
        context = new_context;
    }

    return {
        analyze: analyze,
        set_context: set_context
    }
});
