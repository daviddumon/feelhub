define(["jquery", "modules/flow", "view/flow/feeling-view"],

    function ($, flow, view) {

        var container = "#feeling_form";
        var create_feeling_api_end_point = "/api/feelings";
        var list_feeling_api_end_point = root + "/api/topic/" + topicData.id + "/feelings";

        function init() {
            setFormBehavior();
            flow.init(list_feeling_api_end_point, null, view, null);
        }

        function setFormBehavior() {
            setTextareaBehavior();
            setSubmitBehavior();
            setSelectedLanguage();
        }

        function setTextareaBehavior() {
            $("#feeling_form textarea").focus(function () {
                $("#feeling_form textarea").height("100px");
            });
        }

        function setSubmitBehavior() {
            $(container).submit(function () {
                if ($("#feeling_form textarea").val() !== "") {
                    $.ajax({
                        url: root + create_feeling_api_end_point,
                        type: 'POST',
                        data: $(container).serialize(),
                        success: success,
                        error: error
                    });
                }
                return false;
            });
        }

        function setSelectedLanguage() {
            $("#feeling_form select option[value=" + languageCode + "]").prop('selected', true);
        }

        function success() {
            document.location.reload(true);
        }

        function error() {
        }

        return {
            init: init
        };
    });