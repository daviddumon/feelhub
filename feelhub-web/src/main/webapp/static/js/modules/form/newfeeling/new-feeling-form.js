define(["jquery", "modules/parser/parser", "modules/form/newfeeling/helper-panel", "modules/form/newfeeling/topics-panel"],

    function ($, parser, helper, topics) {

        var container = "#feeling-form";
        var create_feeling_api_end_point = "/api/feelings";
        var analyze_text_timer;

        $("html").on("click", ".form-help .ignore-button", function () {
            $("#form-right-panel").trigger("next-question");
        });

        function init() {
            add_topic();
            helper.draw();
            set_button_behavior();
            set_textarea_behavior();
            set_selected_language();
        }

        function add_topic() {
            if (typeof topicData.id != "undefined") {
                var data = {"name": topicData.name.toLowerCase(), "id": topicData.id, "type": topicData.type, "thumbnailSmall": topicData.thumbnailSmall, "sentiment": "none"};
                var topic = topics.add_topic(data, true);
                helper.add_questions(topic, false);
            }
        }

        function set_button_behavior() {
            if ($(container + " textarea").val() == "") {
                $(container + " .help_text").show();
            }

            $("#form-right-panel").on("click", "input[type=submit]", function (event) {
                event.stopImmediatePropagation();
                event.preventDefault();
                helper.show_wait();
                var data = JSON.stringify({"languageCode": get_language_code(), "text": get_form_text(), "topics": get_topics()});
                $.ajax({
                    type: "POST",
                    url: root + "/api/feelings",
                    contentType: 'application/json',
                    data: data,
                    processData: false,
                    dataType: 'json',
                    success: success,
                    error: error
                });
            });
        }

        function get_language_code() {
            return $("select[name=language] :selected").val();
        }

        function get_form_text() {
            return $("#feeling-form textarea").val();
        }

        function get_topics() {
            var result = new Array();
            var topic_list = topics.get_topics();
            $.each(topic_list, function (index, data) {
                var element = {
                    "id": data.id,
                    "name": data.name,
                    "type": data.type,
                    "sentiment": data.sentiment
                };
                result.push(element);
            });
            return result;
        }

        function success() {
            setTimeout(function () {
                document.location.reload(true);
            }, 1000);
        }

        function error() {
            helper.draw();
        }

        function set_textarea_behavior() {
            $(container + " textarea").keyup(function (event) {
                helper.invalidate();
                $(this).parent().find(".help_text").hide();
                if (event.shiftKey) {
                    var code = event.keyCode || event.which;
                    if (code == 13) {
                        submit();
                        return false;
                    }
                }

                clearTimeout(analyze_text_timer);
                analyze_text_timer = setTimeout(function () {
                    analyze_text($(container + " textarea").val());
                }, 1000);
            });

            $(container + " textarea").focusout(function () {
                if ($(this).val() == "") {
                    $(this).parent().find(".help_text").show();
                }
            });
        }

        function analyze_text(text) {
            var results = parser.analyze(text);
            topics.invalidate();
            $.each(results, function (index, result) {
                if (is_not_current_topic(result)) {
                    var topic = topics.add_topic(result);
                    helper.add_questions(topic);
                }
            });
            helper.draw();
        }

        function is_not_current_topic(result) {
            if (typeof topicData.name == "undefined") {
                return true;
            } else {
                return result.name !== topicData.name.toLowerCase() && is_different_from_uris(result.name);
            }
        }

        function is_different_from_uris(text) {
            var result = true;
            $.each(topicData.uris, function (index, uri) {
                if (text === uri) {
                    result = false;
                }
            });
            return result;
        }


        function set_selected_language() {
            $("#feeling-form select option[value=" + languageCode + "]").prop("selected", true);
        }

        return {
            init: init
        };
    });