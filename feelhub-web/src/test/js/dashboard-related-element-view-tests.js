require.config({
    baseUrl: '/test/',
    paths: {
        'jquery': 'src/test/js/mock/jquery-mock',
        'hogan': 'src/main/webapp/static/js/plugins/hogan',
        'text': 'src/main/webapp/static/js/plugins/text',
        'templates': 'src/main/webapp/static/js/view/templates',
        'plugins': 'src/main/webapp/static/js/plugins',
        'view': 'src/main/webapp/static/js/view'
    }
});

require(['jquery', 'view/dashboard/dashboard-related-element-view'], function($, view) {

    TestCase('DashboardRelatedElementViewTests', {

        setUp: function() {
            JsHamcrest.Integration.JsTestDriver();

            /*:DOC += <div id='container'></div> */
        },

        testCanRender: function() {
            view.render({}, true, '#container');

            assertThat($("#container").children().length, greaterThan(0));
        }
    });
});