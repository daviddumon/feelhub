define(["jquery",
    "view/command/new-feeling-helper-feel-view",
    "view/command/new-feeling-helper-choice-view",
    "view/command/new-feeling-submit-view",
    "view/command/new-feeling-helper-category-view",
    "view/command/new-feeling-wait-view"],

    function ($, helper_feel_view, helper_choice_view, submit_view, helper_category_view, wait_view) {

        var uri = /((http|https):\/\/)?([%a-zA-Z_0-9\.-]+)(\.[a-z]{2,3}){1}(\/.*$)?/i;
        var known_questions = [];
        var active_questions = [];
        var current_question = null;

        function add_questions(topic, invalidate) {
            invalidate = (invalidate != null) ? invalidate : true;
            topic = topic || {"name": ""};
            if (topic.name !== "") {
                add_question_feel(topic, invalidate);
                add_question_choice(topic, invalidate);
                add_existing_question_category(topic);
            }
        }

        function add_question_feel(topic, invalidate) {
            var index = get_known_question_index(helper_feel_view, topic);
            if (index < 0) {
                create_new_feel_question(topic, invalidate);
            } else {
                activate_known_question(index);
            }
        }

        function create_new_feel_question(topic, invalidate) {
            push_known_question(helper_feel_view, topic, {}, invalidate);
            if (topic.sentiment === "none") {
                push_active_question(helper_feel_view, topic, {}, invalidate);
            }
        }

        function add_question_choice(topic, invalidate) {
            if (topic.id === "") {
                var index = get_known_question_index(helper_choice_view, topic);
                if (index < 0) {
                    search_known_topics(topic, invalidate);
                } else {
                    activate_known_question(index);
                }
            }
        }

        function search_known_topics(topic, invalidate) {
            $.getJSON(root + "/api/topics?q=" + topic.name, function (data) {
                if (data.length == 0) {
                    $("#form-left-panel").trigger("set-topic", {"name": topic.name, "id": "new", "thumbnail": root + "/static/images/new.jpg"});
                } else {
                    if (uri.test(topic.name) && data.length == 1) {
                        $("#form-left-panel").trigger("set-topic", {"name": topic.name, "id": data[0].id, "thumbnail": data[0].thumbnail});
                    } else {
                        push_known_question(helper_choice_view, topic, data, invalidate);
                        push_active_question(helper_choice_view, topic, data, invalidate);
                    }
                }
            });
        }

        function add_existing_question_category(topic) {
            var index = get_known_question_index(helper_category_view, topic);
            if (index >= 0) {
                activate_known_question(index);
            }
        }

        function activate_known_question(index) {
            var known_question = known_questions[index];
            if (!known_question.answered) {
                active_questions.push(known_question);
            }
        }

        function push_known_question(view, topic, optionaldata, invalidate) {
            var question = {"view": view, "data": {"topic": topic, "other": optionaldata}, "invalidate": invalidate, "answered": false};
            known_questions.push(question);
        }

        function push_active_question(view, topic, optionaldata, invalidate) {
            var question = {"view": view, "data": {"topic": topic, "other": optionaldata}, "invalidate": invalidate, "answered": false};
            active_questions.push(question);
            draw();
        }

        $("html").on("next-question", "#form-right-panel", function (event, save) {
            if (save) {
                var index = get_known_question_index(current_question.view, current_question.data.topic);
                known_questions[index].data.topic.type = save;
            }
            next_question();
        });

        function next_question() {
            set_answered();
            if (active_questions.length > 0) {
                active_questions.shift();
            }
            draw();
        }

        function set_answered() {
            if (current_question != null) {
                var index = get_known_question_index(current_question.view, current_question.data.topic);
                known_questions[index].answered = true;
            }
        }

        $("html").on("topic-click", "#form-right-panel", function (event, name) {
            set_active({"name": name});
        });

        function set_active(topic) {
            set_active_for_view(helper_category_view, topic);
            set_active_for_view(helper_choice_view, topic);
            set_active_for_view(helper_feel_view, topic);
            draw();
        }

        function set_active_for_view(view, topic) {
            var known_question_index = get_known_question_index(view, topic);
            if (known_question_index >= 0) {
                var active_question_index = get_active_question_index(known_questions[known_question_index]);
                if (active_question_index < 0) {
                    active_questions.unshift(known_questions[known_question_index]);
                } else {
                    var active_question = active_questions.splice(active_question_index, 1);
                    active_questions.unshift(active_question[0]);
                }
            }
        }

        function get_known_question_index(view, topic) {
            var question_index = -1;
            $.each(known_questions, function (index, question) {
                if (is_equal(question, question_for_topic(topic, view))) {
                    question_index = index;
                }
            });
            return question_index;
        }

        function get_active_question_index(known_question) {
            var question_index = -1;
            $.each(active_questions, function (index, active_question) {
                if (is_equal(active_question, known_question)) {
                    question_index = index;
                }
            });
            return question_index;
        }

        function is_equal(left, right) {
            return left.data.topic.name === right.data.topic.name && left.view === right.view;
        }

        function question_for_topic(topic, view) {
            return {"view": view, "data": {"topic": topic, "other": {}}};
        }

        function invalidate() {
            for (var i = active_questions.length - 1; i >= 0; i--) {
                if (active_questions[i].invalidate) {
                    active_questions.splice(i, 1);
                }
            }
            hide_current_question();
        }

        function draw() {
            if (has_questions_to_draw()) {
                if (is_next_question_different_from_current_question()) {
                    hide_current_question();
                    draw_next_question();
                }
            } else {
                hide_current_question();
                add_submit_question();
            }
        }

        function has_questions_to_draw() {
            return active_questions.length > 0;
        }

        function is_next_question_different_from_current_question() {
            return current_question == null || !is_equal(current_question, active_questions[0]);
        }

        function hide_current_question() {
            if (current_question != null) {
                (current_question.view.clear)();
                current_question = null;
            }
            submit_view.clear();
        }

        function draw_next_question() {
            current_question = active_questions[0];
            (current_question.view.render)(current_question.data, current_question.invalidate);
        }

        function add_submit_question() {
            submit_view.render();
        }

        function show_wait() {
            $(".form-help").empty();
            wait_view.render();
        }

        $("html").on("category-question", "#form-right-panel", function (event, topic) {
            add_category_question(topic);
        });

        function add_category_question(topic) {
            var index = get_known_question_index(helper_category_view, topic);
            if (index < 0) {
                push_known_question(helper_category_view, topic, {}, true);
                push_active_question(helper_category_view, topic, {}, true);
            } else {
                activate_known_question(index);
            }
        }

        //testing
        function test_reset() {
            known_questions = [];
            active_questions = [];
            current_question = null;
        }

        function get_known_questions() {
            return known_questions;
        }

        function get_active_questions() {
            return active_questions;
        }

        return {
            add_questions: add_questions,
            set_active: set_active,
            next_question: next_question,
            invalidate: invalidate,
            add_category_question: add_category_question,
            show_wait: show_wait,
            draw: draw,
            // for testing purpose
            test_reset: test_reset,
            get_known_questions: get_known_questions,
            get_active_questions: get_active_questions
        }
    });