define(["modules/form/newfeeling/helper-panel", "view/command/new-feeling-helper-feel-view", "view/command/new-feeling-helper-category-view"],

    function (helper, helper_feel_view, helper_category_view) {

        describe("helper-panel tests suite", function () {

            var topic_with_id = {
                "name": "topic1",
                "id": "id",
                "thumbnailSmall": "thumbnailSmall",
                "sentiment": "none"
            };

            var topic_without_id = {
                "name": "topic2",
                "id": "",
                "thumbnailSmall": "thumbnailSmall",
                "sentiment": "none"
            };

            var topic_with_sentiment = {
                "name": "topic3",
                "id": "",
                "thumbnailSmall": "thumbnailSmall",
                "sentiment": "good"
            }

            var topic_without_name = {
                "name": "",
                "id": "id",
                "thumbnailSmall": "thumbnailSmall",
                "sentiment": "none"
            }

            var callback_data = [
                {"id": "id1"}, {"id": "id2"}
            ];

            beforeEach(function () {
                helper.test_reset();
                $('html').empty();
                $("html").append("<div id='form-right-panel'></div><div id='form-left-panel'></div>");
            });

            it("adds question for sentiment with sentiment value of none", function () {
                helper.add_questions(topic_with_id);

                expect(helper.get_known_questions().length).toEqual(1);
            });

            it("only adds questions for sentiment with names", function () {
                helper.add_questions(topic_without_name);

                expect(helper.get_known_questions().length).toEqual(0);
            });

            it("executes view when added", function () {
                spyOn(helper_feel_view, "render");

                helper.add_questions(topic_with_id);

                expect(helper_feel_view.render).toHaveBeenCalled();
            });

            it("doest not add twice the same question", function () {
                helper.add_questions(topic_with_id);
                helper.add_questions(topic_with_id);

                expect(helper.get_known_questions().length).toEqual(1);
            });

            it("does not execute render function if a current question is already set", function () {
                spyOn(helper_feel_view, "render");

                helper.add_questions(topic_with_id);
                helper.add_questions(topic_without_id);

                expect(helper_feel_view.render.calls.length).toEqual(1);
            });

            it("calls render function with good data", function () {
                spyOn(helper_feel_view, "render");

                helper.add_questions(topic_with_id);

                expect(helper_feel_view.render).toHaveBeenCalledWith({"topic": topic_with_id, "other": {}});
            });

            it("executes render function when set as active with an already current function", function () {
                spyOn(helper_feel_view, "render");
                helper.add_questions(topic_with_id);
                helper.add_questions(topic_without_id);

                helper.set_active(topic_without_id);

                expect(helper_feel_view.render.calls.length).toEqual(2);
            });

            it("executes end of current question when set a new active question", function () {
                spyOn(helper_feel_view, "clear");
                helper.add_questions(topic_with_id);
                helper.add_questions(topic_without_id);

                helper.set_active(topic_without_id);

                expect(helper_feel_view.clear).toHaveBeenCalled();
            });

            it("executes next question start when asked", function () {
                spyOn(helper_feel_view, "render");
                helper.add_questions(topic_without_id);
                helper.add_questions(topic_with_id);

                helper.next_question();

                expect(helper_feel_view.render.calls.length).toEqual(2);
            });

            it("executes current question end when asked next question", function () {
                spyOn(helper_feel_view, "clear");
                helper.add_questions(topic_with_id);
                helper.add_questions(topic_without_id);

                helper.next_question();

                expect(helper_feel_view.clear).toHaveBeenCalled();
            });

            it("can invalidate a question", function () {
                helper.add_questions(topic_with_id);

                helper.invalidate();

                expect(helper.get_active_questions().length).toEqual(0);
            });

            it("only invalidate functions with boolean set to true", function () {
                helper.add_questions(topic_with_id, false);

                helper.invalidate();

                expect(helper.get_known_questions().length).toEqual(1);
            });

            it("can invalidate multiple functions", function () {
                helper.add_questions(topic_with_id);
                helper.add_questions(topic_without_id);

                helper.invalidate();

                expect(helper.get_active_questions().length).toEqual(0);
            });

            it("sets current question to null if no other question", function () {
                spyOn(helper_feel_view, "render");
                helper.add_questions(topic_with_id);
                helper.next_question();

                helper.set_active(topic_with_id);

                expect(helper_feel_view.render.calls.length).toEqual(2);
            });

            it("uses existing questions when setting active topic", function () {
                helper.add_questions(topic_with_id);

                helper.set_active(topic_with_id);

                expect(helper.get_known_questions().length).toEqual(1);
                expect(helper.get_active_questions().length).toEqual(1);
            });

            it("can search for known topics if no id", function () {
                spyOn($, "getJSON");

                helper.add_questions(topic_with_sentiment);

                expect($.getJSON).toHaveBeenCalledWith(root + "/api/topics?q=" + topic_with_sentiment.name, jasmine.any(Function));
            });

            it("creates questions when receiving known topics list", function () {
                spyOn($, "getJSON").andCallFake(function (adr, callback) {
                    (callback)(callback_data);
                });

                helper.add_questions(topic_with_sentiment);

                expect(helper.get_known_questions().length).toEqual(2);
            });

            it("creates choice question only if previous existing topics", function () {
                spyOn($, "getJSON").andCallFake(function (adr, callback) {
                    (callback)([]);
                });

                helper.add_questions(topic_with_sentiment);

                expect(helper.get_known_questions().length).toEqual(1);
            });

            it("triggers set-topic event if no choices", function () {
                var callback = jasmine.createSpy("callback");
                $("#form-left-panel").on("set-topic", callback);
                spyOn($, "getJSON").andCallFake(function (adr, callback) {
                    (callback)([]);
                });

                helper.add_questions(topic_with_sentiment);

                expect(callback).toHaveBeenCalled();
            });

            it("passes invalidate boolean to all questions", function () {
                spyOn($, "getJSON").andCallFake(function (adr, callback) {
                    (callback)(callback_data);
                });

                helper.add_questions(topic_with_sentiment, false);

                expect(helper.get_known_questions()[0].invalidate).toBe(false);
            });

            it("has list of known topics in question data", function () {
                spyOn($, "getJSON").andCallFake(function (adr, callback) {
                    (callback)(callback_data);
                });

                helper.add_questions(topic_with_sentiment);

                expect(helper.get_active_questions()[0].data.other).toBe(callback_data);
            });

            it("sets active on topic-click event", function () {
                helper.add_questions(topic_with_id)
                helper.next_question();
                $("#form-right-panel").trigger("topic-click", topic_with_id.name);

                expect(helper.get_active_questions().length).toEqual(1);
            });

            it("renders next question on next-question event", function () {
                spyOn(helper_feel_view, "render");
                helper.add_questions(topic_without_id);
                helper.add_questions(topic_with_id);

                $("#form-right-panel").trigger("next-question");

                expect(helper_feel_view.render.calls.length).toEqual(2);
            });

            it("reactivates known questions when adding a known topic", function () {
                spyOn($, "getJSON").andCallFake(function (adr, callback) {
                    (callback)(callback_data);
                });
                helper.add_questions(topic_without_id);
                helper.invalidate();

                helper.add_questions(topic_without_id);

                expect($.getJSON.calls.length).toEqual(1);
            });

            it("can not set active unknown questions", function () {
                helper.set_active(topic_with_id);

                expect(helper.get_known_questions().length).toEqual(0);
                expect(helper.get_active_questions().length).toEqual(0);
            });

            it("do not activate sentiment question if existing", function () {
                helper.add_questions(topic_with_sentiment);

                expect(helper.get_known_questions().length).toEqual(1);
                expect(helper.get_active_questions().length).toEqual(0);
            });

            it("sets current question to answered on next-question event", function () {
                helper.add_questions(topic_with_id);

                $("#form-right-panel").trigger("next-question");

                expect(helper.get_known_questions()[0].answered).toBe(true);
            })

            it("does not reactivate known questions if answered", function () {
                spyOn(helper_feel_view, "render");
                helper.add_questions(topic_with_id);
                helper.next_question();

                helper.add_questions(topic_with_id);

                expect(helper_feel_view.render.calls.length).toEqual(1);
            });

            it("triggers set-topic event for uri names", function () {
                var callback = jasmine.createSpy("callback");
                $("#form-left-panel").on("set-topic", callback);
                spyOn($, "getJSON").andCallFake(function (adr, json_callback) {
                    (json_callback)([{"id":"id1"}]);
                });

                helper.add_questions({"name": "www.feelhub.com", "id": ""});

                expect(callback).toHaveBeenCalled();
            });

            it("can add a category question", function () {
                helper.add_category_question(topic_with_id);

                expect(helper.get_known_questions().length).toEqual(1);
                expect(helper.get_active_questions().length).toEqual(1);
            });

            it("do not duplicate category question", function () {
                helper.add_category_question(topic_with_id);
                helper.invalidate();
                helper.add_category_question(topic_with_id);

                expect(helper.get_known_questions().length).toEqual(1);
                expect(helper.get_active_questions().length).toEqual(1);
            });

            it("adds category question on category-question event", function () {
                $("#form-right-panel").trigger("category-question", topic_with_id);

                expect(helper.get_known_questions().length).toEqual(1);
                expect(helper.get_active_questions().length).toEqual(1);
            });

            it("renders category question when set active", function () {
                spyOn(helper_category_view, "render");
                helper.add_category_question(topic_with_id);

                helper.set_active(topic_with_id);

                expect(helper_category_view.render.calls.length).toEqual(1);
            });

            it("redraw known category questions", function () {
                helper.add_category_question(topic_with_id);
                helper.invalidate();

                helper.add_questions(topic_with_id);

                expect(helper.get_active_questions().length).toEqual(2);
            });

            it("can save data on next question", function () {
                helper.add_questions(topic_with_id);

                $("#form-right-panel").trigger("next-question", "other");

                expect(helper.get_known_questions()[0].data.topic.type).toBe("other");
            });
        });
    });