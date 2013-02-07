define(['jquery', 'modules/newfeeling'], function ($, newfeeling) {

    describe('new feeling tests', function () {

        var container = "#feeling_form";

        beforeEach(function () {
            $("html").append("<body>" +
                "<form id='feeling_form'>" +
                "<div class='hidden-form'>" +
                "<textarea></textarea>" +
                "<select name='language'>" +
                "<option value=''></option>" +
                "<option value='fr'>french</option>" +
                "</select>" +
                "</div>" +
                "<button/>" +
                "</form> " +
                "</body>");
            $("body").append("<script type='text/javascript'>var languageCode = 'fr';</script>");
        });

        it('show hidden form on first click', function () {
            newfeeling.init();

            $(container + " button").click();

            expect($(container + " .hidden-form").css("display")).toBe("inline");
        });

        it('post new feeling on second click', function () {
            newfeeling.init();

            $(container + " button").click();
            $(container + " button").click();


        });

        it('select good language on load', function () {
            newfeeling.init();

            expect($(container + " select :selected").html()).toBe("french");
        });
    });
});