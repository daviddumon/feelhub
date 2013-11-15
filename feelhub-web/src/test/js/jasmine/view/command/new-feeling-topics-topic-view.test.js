define(["jquery","view/command/new-feeling-topics-topic-view"], function ($, view) {

    describe("new-feeling-topics-topic-view tests", function () {

        var data = {
            "name": "name1",
            "sentiment": "happy",
            "id": "myid",
            "thumbnail": "thumb"
        };

        beforeEach(function () {
            $("html").empty();
            $("html").append("<body><div id='form-topics'></div><div id='form-right-panel'></div></body>");
        });

        it("can render sentiment", function () {
            view.render(data);

            expect($(".topic-form").length).toEqual(1);
        });

        it("can empty the container", function () {
            view.render(data);

            view.reset();

            expect($(".topic-form").length).toEqual(0);
        });

        it("can trigger custom event on topic click", function () {
            view.render(data);
            var callback = jasmine.createSpy("callback");
            $("#form-right-panel").on("topic-click", callback);

            $(".topic-form").click();

            expect(callback).toHaveBeenCalled();
        });
    });
});