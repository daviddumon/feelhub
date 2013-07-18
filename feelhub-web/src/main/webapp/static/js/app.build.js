({
    appDir: "controller",
    baseUrl: ".",
    dir: "controller-built",
    modules: [
        {
            name: "topic-controller"
        },
        {
            name: "bookmarklet-controller"
        },
        {
            name: "fixed-controller"
        },
        {
            name: "home-controller"
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
        templates: "../templates",
        hogan: "../plugins/hogan",
        text: "../plugins/text"
    },
    preserveLicenseComments: false,
    stubModules: ['text', 'hgn'],
    pragmasOnSave: {
        excludeHogan: true
    }
})