define(["jquery"], function ($) {

    var happy_tokens = /:\)|\+/g;
    var sad_tokens = /:\(|-/g;
    var bored_tokens = /:\||=/g;

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
        var happy = 0, sad = 0, bored = 0;
        for (var i = 0; i < (text.match(happy_tokens) || []).length; i++) {
            happy++;
        }
        for (var i = 0; i < (text.match(sad_tokens) || []).length; i++) {
            sad++;
        }
        for (var i = 0; i < (text.match(bored_tokens) || []).length; i++) {
            bored++;
        }
        return sentiment_for(happy, sad, bored);
    }

    function sentiment_for(happy, sad, bored) {
        if (happy > sad && happy > bored) {
            return "happy";
        } else if (sad > happy && sad > bored) {
            return "sad";
        } else if (bored > happy && bored > sad) {
            return "bored";
        } else {
            return "none";
        }
    }

    return {
        get_sentiment: get_sentiment
    }
});