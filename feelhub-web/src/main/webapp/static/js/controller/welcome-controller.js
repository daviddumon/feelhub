require.config(
    {
        paths: {
            "jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min",
            "view": "../view",
            "modules": "../modules",
            "plugins": "../plugins",
            "templates": "../templates",
            "hogan": "../plugins/hogan",
            "text": "../plugins/text"
        },
        waitSeconds: 0
    }
);

require(["plugins/domReady!", "modules/authentification", "modules/messages"],

    function (doc, authentification, messages) {
        messages.init();
        $("#signup").show();
        authentification.init();

        $("body").on("click", "#login-button", function () {
            $("#signup").hide();
            $("#login").show();
        });

        $("body").on("click", "#signup-button", function () {
            $("#login").hide();
            $("#signup").show();
        });
    });