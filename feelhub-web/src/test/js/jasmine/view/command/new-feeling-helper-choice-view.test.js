define(["jquery", "view/command/new-feeling-helper-choice-view"], function ($, view) {

    describe("helper choice view tests", function () {

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

            $(".topic-choice div").click();

            expect(callback).toHaveBeenCalled();
        });

        it("triggers set-topic event on image click", function () {
            view.render({});
            var callback = jasmine.createSpy("callback");
            $("#form-left-panel").on("set-topic", callback);

            $(".topic-choice div").click();

            expect(callback).toHaveBeenCalled();
        });
    });
});