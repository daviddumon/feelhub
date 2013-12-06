({
    appDir: "controller",
    baseUrl: ".",
    dir: "controller-built",
    modules: [
        {
            name: "topic-controller"
        },
        {
            name: "topic-crawlable-controller"
        },
        {
            name: "bookmarklet-controller"
        },
        {
            name: "fixed-controller"
        },
        {
            name: "welcome-controller"
        },
        {
            name: "home-controller"
        }
    ],
    paths: {
        jquery: "empty:",
        view: "../view",
        modules: "../modules",
        plugins: "../plugins",
        templates: "../templates",
        hogan: "../plugins/hogan",
        text: "../plugins/text"
    },
    shim : {
        bootstrap:{
            deps : ['jquery']
        }
    },
    preserveLicenseComments: false,
    stubModules: ['text', 'hgn'],
    pragmasOnSave: {
        excludeHogan: true
    },
    waitSeconds: 0
})