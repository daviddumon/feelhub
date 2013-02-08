var root = "root";

define(["modules/form/newfeeling/topics-panel", "view/command/new-feeling-topics-topic-view"], function (topics, view) {

    describe("topics-panel form tests", function () {

        var data = {
            "name": "name1",
            "sentiment": "good",
            "id": "myid",
            "thumbnailSmall": "thumb"
        };

        beforeEach(function () {
            topics.test_reset();
            $('html').empty();
            $("html").append("<div id='form-right-panel'></div><div id='form-left-panel'></div>");
        });

        it("can add a sentiment", function () {
            topics.add_topic(data);

            expect(topics.get_topics().length).toEqual(1);
        });

        it("can use data for sentiment creation", function () {
            topics.add_topic(data);

            var topic = topics.get_topics()[0];
            expect(topic.name).toBe(data.name);
            expect(topic.sentiment).toBe(data.sentiment);
            expect(topic.id).toBe(data.id);
            expect(topic.thumbnailSmall).toBe(data.thumbnailSmall);
        });

        it("has default values for sentiment data", function () {
            topics.add_topic({"name": "name"});

            var topic = topics.get_topics()[0];
            expect(topic.sentiment).toBe("none");
            expect(topic.id).toBe("");
            expect(topic.thumbnailSmall).toBe("");
        });

        it("does not create sentiment without name", function () {
            topics.add_topic({});

            expect(topics.get_topics().length).toEqual(0);
        });

        it("adds sentiment only once", function () {
            topics.add_topic(data);
            topics.add_topic(data);

            expect(topics.get_topics().length).toEqual(1);
        });

        it("can invalidate existing sentiments", function () {
            topics.add_topic(data);

            topics.invalidate();

            expect(topics.get_topics().length).toEqual(1);
            var topic = topics.get_topics()[0];
            expect(topic.active).toBe(false);
        });

        it("has permanent option", function () {
            topics.add_topic(data, true);

            var topic = topics.get_topics()[0];
            expect(topic.permanent).toBe(true);
        });

        it("has default permanent value of false", function () {
            topics.add_topic(data);

            var topic = topics.get_topics()[0];
            expect(topic.permanent).toBe(false);
        });

        it("only invalidates non permanent sentiments", function () {
            topics.add_topic(data, true);

            topics.invalidate();

            expect(topics.get_topics().length).toEqual(1);
            var topic = topics.get_topics()[0];
            expect(topic.active).toBe(true);
        });

        it("draws newly added sentiment", function () {
            spyOn(view, "render");

            topics.add_topic(data);

            expect(view.render).toHaveBeenCalled();
        });

        it("reset the view when invalidate", function () {
            spyOn(view, "reset");

            topics.invalidate();

            expect(view.reset).toHaveBeenCalled();
        });

        it("redraw all actives sentiments when invalidate", function () {
            topics.add_topic(data, true);
            spyOn(view, "render");

            topics.invalidate();

            expect(view.render).toHaveBeenCalled();
        });

        it("updates existing sentiment with new sentiment from text", function () {
            topics.add_topic({"name": data.name, "sentiment": "none"});
            topics.invalidate();

            topics.add_topic({"name": data.name, "sentiment": "bad"});

            var topic = topics.get_topics()[0];
            expect(topic.sentiment).toBe("bad");
        });

        it("has a boolean for the question answer on sentiment", function () {
            topics.add_topic(data);

            var topic = topics.get_topics()[0];
            expect(topic.unset).toBe(true);
        });

        it("can change sentiment of topic when sentiment-change event is triggered", function () {
            topics.add_topic(data);

            $("#form-left-panel").trigger("sentiment-change", {"name": data.name, "sentiment": "bad"});

            var topic = topics.get_topics()[0];
            expect(topic.sentiment).toBe("bad");
        });

        it("sets topic on set-topic event", function () {
            topics.add_topic(data);

            $("#form-left-panel").trigger("set-topic", {"name": data.name, "id": "new"});

            var topic = topics.get_topics()[0];
            expect(topic.id).toBe("new");
        });

        it("triggers category-question if set-topic event with new id", function () {
            topics.add_topic(data);
            var callback = jasmine.createSpy("callback");
            $("#form-right-panel").on("category-question", callback);

            $("#form-left-panel").trigger("set-topic", {"name": data.name, "id": "new"});

            expect(callback).toHaveBeenCalled();
        });

        it("doest not trigger category-question for uri", function () {
            topics.add_topic({"name": "www.feelhub.com"});
            var callback = jasmine.createSpy("callback");
            $("#form-right-panel").on("category-question", callback);

            $("#form-left-panel").trigger("set-topic", {"name": "www.feelhub.com", "id": "new"});

            expect(callback).not.toHaveBeenCalled();
        });

        it("sets category on set-category event", function () {
            topics.add_topic(data);

            $("#form-left-panel").trigger("set-category", {"name": data.name, "type": "other"});

            var topic = topics.get_topics()[0];
            expect(topic.type).toBe("other");
        });
    });
})

