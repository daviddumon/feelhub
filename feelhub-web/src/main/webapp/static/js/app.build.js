({
    appDir: "controller",
    baseUrl: ".",
    dir: "controller-built",
    modules: [
        {
            name: "topic-controller"
        },
        {
            name: "signup-controller"
        },
        {
            name: "activation-controller"
        },
        {
            name: "common-controller"
        },
        {
            name: "home-controller"
        },
        {
            name: "login-controller"
        },
        {
            name: "myfeelings-controller"
        },
        {
            name: "newtopic-controller"
        },
        {
            name: "search-controller"
        }
    ],
    paths: {
        jquery: "empty:",
        view: "../view",
        modules: "../modules",
        plugins: "../plugins",
        templates: "../view/templates",
        hogan: "../plugins/hogan",
        text: "../plugins/text"
    },
    preserveLicenseComments: false
})