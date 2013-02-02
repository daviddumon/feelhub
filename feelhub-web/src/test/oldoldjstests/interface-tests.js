require.config({
    baseUrl: '/test/',
    paths: {
        'jquery': 'src/test/js/mock/jquery-mock',
        'modules': 'src/main/webapp/static/js/modules'
    }
});

require(['jquery', 'modules/interface'], function ($, interface) {

    TestCase('InterfaceTests', {

        setUp: function () {
            JsMockito.Integration.JsTestDriver();

            /*:DOC += <a href="javascript:void(0);" id="logout">logout</a> */

            document.location.reload = mockFunction();
        },

        testDeleteSessionOnlogout: function () {
            interface.init();

            $("#logout").click();

            var json = $.ajaxcall;
            assertNotUndefined(json);
            assertSame(root + '/sessions', json.url);
            assertSame('DELETE', json.type);
        },

        testOnSuccessReloadLocation: function() {
            interface.init();
            $("#logout").click();

            $.ajaxcall.success();

            verify(document.location.reload);
        }
    });
});