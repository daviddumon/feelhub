var languageCode = "en";

require.config({
    baseUrl: '/test/',
    paths: {
        'jquery': 'src/test/js/mock/jquery-mock',
        'modules': 'src/main/webapp/static/js/modules'
    }
});

require(['jquery', 'modules/topic'], function ($, topic) {

    TestCase('TopicTests', {

        setUp: function () {
            JsMockito.Integration.JsTestDriver();

            /*:DOC += <form id='feeling_form'>
             <textarea name="text" style="height: 60px;">
             </textarea>
             <select>
             <option value="fr">fr</option>
             <option value="en">en</option>
             <option value="de">de</option>
             </select>
             </form> */

            document.location.reload = mockFunction();
        },

        testFeelingFormShouldExpandOnFocus: function () {
            topic.init();

            $('#feeling_form textarea').focus();

            assertSame(100, $("#feeling_form textarea").height());
        },

        testPostFeelingOnSubmit: function () {
            topic.init();

            $("form").submit();

            var json = $.ajaxcall;
            assertNotUndefined(json);
            assertSame(root + '/api/feelings', json.url);
            assertSame('POST', json.type);
            assertSame($("form").serialize(), json.data);
        },

        testRedirectOnSuccess: function () {
            topic.init();
            $("form").submit();

            $.ajaxcall.success();

            verify(document.location.reload)(true);
        },

        testSetLanguageCodeSelectedInTheForm: function() {
            topic.init();

            assertSame(languageCode, $("select option:selected").val());
        }
    });
});