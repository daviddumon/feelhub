require.config({
    baseUrl: '/test/',
    paths: {
        'jquery': 'src/test/js/mock/jquery-mock'
    }
});

require(['jquery'], function($) {

    TestCase('FlowTests', {

        setUp: function() {

        }


    });
});