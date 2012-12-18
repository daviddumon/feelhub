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

require(['jquery', 'view/dashboard/dashboard-view'], function($, view) {

    TestCase('DashboardViewTests', {

        setUp: function() {
            JsHamcrest.Integration.JsTestDriver();

            /*:DOC += <div id='dashboard'></div> */
        },

        testCanRender: function() {
            view.render({}, true);

            assertThat($("#dashboard").children().length, greaterThan(0));
        }
    });
});