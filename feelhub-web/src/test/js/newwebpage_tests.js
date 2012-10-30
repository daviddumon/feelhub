var NewSubjectTests = TestCase("NewSubjectTests");

NewSubjectTests.prototype = {

    setUp: function() {

        $.post = function(url, data, callback) {
            postUrl = url;
            postData = data;
            var xhr = {
                getResponseHeader : function(key) {
                    if (key == "Location") {
                        return "feelhub location";
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
        newwebpage.create("uritocreate");

        assertEquals("http://localhost/webpages", postUrl);
        assertEquals("uritocreate", postData.uri);
        assertEquals("feelhub location", redirectUri);
    }
};

var originalPost = $.post;
var originalRedirect = $.redirect;
var postUrl;
var postData;
var redirectUri;
var root = "http://localhost";