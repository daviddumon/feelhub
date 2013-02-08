var root = "root";

define(["jquery", "view/command/new-feeling-helper-feel-view"], function ($, view) {

    describe("helper-feel view tests", function () {

        beforeEach(function () {
            $('html').empty();
            $("html").append("<div id='form-right-panel'></div><div id='form-left-panel'></div>");
        });

        it("can render the view", function () {
            view.render({});

            expect($(".form-help").length).toEqual(1);
        });

        it("can clear the view", function () {
            view.render({});

            view.clear();

            expect($(".form-help").length).toEqual(0);
        });

        it("triggers event sentiment-change on image click", function () {
            view.render({});
            var callback = jasmine.createSpy("callback");
            $("#form-left-panel").on("sentiment-change", callback);

            $("#form-right-panel .sentiment-choice img").click();

            expect(callback).toHaveBeenCalled();
        });

        it("triggers event next-question on image click", function () {
            view.render({});
            var callback = jasmine.createSpy("callback");
            $("#form-right-panel").on("next-question", callback);

            $("#form-right-panel .sentiment-choice img").click();

            expect(callback).toHaveBeenCalled();
        });
    });
});