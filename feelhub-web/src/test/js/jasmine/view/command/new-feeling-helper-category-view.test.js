define(["jquery", "view/command/new-feeling-helper-category-view"], function ($, view) {

    describe("helper category view tests", function () {

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

        it("triggers next-question event on image click", function () {
            view.render({});
            var callback = jasmine.createSpy("callback");
            $("#form-right-panel").on("next-question", callback);

            $(".category-ok").click();

            expect(callback).toHaveBeenCalled();
        });

        it("triggers set-category event on image click", function () {
            view.render({});
            var callback = jasmine.createSpy("callback");
            $("#form-left-panel").on("set-category", callback);

            $(".category-ok").click();

            expect(callback).toHaveBeenCalled();
        });
    });
});