define(["jquery", "modules/form/newfeeling/new-feeling-form", "view/command/new-feeling-helper-feel-view"],

    function ($, new_feeling_form, view) {

        describe("new form feeling tests", function () {

            beforeEach(function () {
                $('html').empty();
                $("html").append("<div id='form-right-panel'></div><div id='form-left-panel'></div>");
                $("body").append("<script type='text/javascript'>var languageCode = 'fr';var topicData = {};</script>");
            });

            it("trigger next-question event on ignore", function () {
                view.render({});
                var callback = jasmine.createSpy("callback");
                $("#form-right-panel").on("next-question", callback);

                $("#form-right-panel .ignore-button").click();

                expect(callback).toHaveBeenCalled();
            });
        });
    });