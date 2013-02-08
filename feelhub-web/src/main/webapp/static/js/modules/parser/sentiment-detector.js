define(["jquery"], function ($) {

    var good_tokens = /:\)|\+/g;
    var bad_tokens = /:\(|-/g;
    var neutral_tokens = /:\||=/g;

    function get_sentiment(token, text, radius) {
        radius = radius || 0;
        var text_with_radius = apply_radius(text, radius, token);
        return extract_sentiment(text_with_radius);
    }

    function apply_radius(text, radius, token) {
        if (radius > 0) {
            var token_index = text.indexOf(token);
            var start = token_index;
            var length = token.length + radius;
            return text.substr(start, length);
        } else {
            return text;
        }
    }

    function extract_sentiment(text) {
        var good = 0, bad = 0, neutral = 0;
        for (var i = 0; i < (text.match(good_tokens) || []).length; i++) {
            good++;
        }
        for (var i = 0; i < (text.match(bad_tokens) || []).length; i++) {
            bad++;
        }
        for (var i = 0; i < (text.match(neutral_tokens) || []).length; i++) {
            neutral++;
        }
        return sentiment_for(good, bad, neutral);
    }

    function sentiment_for(good, bad, neutral) {
        if (good > bad && good > neutral) {
            return "good";
        } else if (bad > good && bad > neutral) {
            return "bad";
        } else if (neutral > good && neutral > bad) {
            return "neutral";
        } else {
            return "none";
        }
    }

    return {
        get_sentiment: get_sentiment
    }
});