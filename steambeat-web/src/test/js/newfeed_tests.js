var NewFeedTests = TestCase("NewFeedTests");

NewFeedTests.prototype = {

    setUp: function() {

        $.post = function(url, data, callback) {
            postUrl = url;
            postData = data;
            var xhr = {
                getResponseHeader : function(key) {
                    if (key == "Location") {
                        return "kikiyoo location";
                    }
                }
            };
            callback(null, "test", xhr);
        }

        $.redirect = function(uri) {
            redirectUri = uri;
        };
    },

    tearDown: function() {
        $.post = originalPost;
        $.redirect = originalRedirect;
    },

    testRedirectOnSuccess: function() {
        newfeed.create("uritocreate");

        assertEquals("http://localhost/feeds", postUrl);
        assertEquals("uritocreate", postData.uri);
        assertEquals("kikiyoo location", redirectUri);
    }
};

var originalPost = $.post;
var originalRedirect = $.redirect;
var postUrl;
var postData;
var redirectUri;
var root = "http://localhost";