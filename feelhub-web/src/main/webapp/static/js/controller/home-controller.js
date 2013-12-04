require.config(
    {
        paths: {
            "jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min",
            "view": "../view",
            "modules": "../modules",
            "plugins": "../plugins",
            "templates": "../templates",
            "hogan": "../plugins/hogan",
            "text": "../plugins/text",
            "reveal":"../plugins/reveal",
            "bootstrap":"../plugins/bootstrap"
        },
        shim: {
          'bootstrap': {
              deps:['jquery']
          }
        },
        waitSeconds: 0
    }
);

require(["plugins/domReady!", "modules/interface", "modules/flow-topics", "modules/add-topic", "bootstrap"],

    function (doc, interface, flow, addTopic) {
        interface.init();
        flow.init();
        addTopic.init();
    });