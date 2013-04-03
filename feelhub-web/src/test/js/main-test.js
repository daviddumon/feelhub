var tests = Object.keys(window.__karma__.files).filter(function (file) {
    return /\.test\.js$/.test(file);
});

require({

    baseUrl: '/base',
    paths: {
        'jquery': 'src/test/js/lib/jquery.min',
        'view': 'src/main/webapp/static/js/view',
        'plugins': 'src/main/webapp/static/js/plugins',
        'modules': 'src/main/webapp/static/js/modules',
        'hogan': 'src/main/webapp/static/js/plugins/hogan',
        'text': 'src/main/webapp/static/js/plugins/text',
        'templates': 'src/main/webapp/static/js/templates'
    },
    deps: tests,
    callback: window.__karma__.start
});