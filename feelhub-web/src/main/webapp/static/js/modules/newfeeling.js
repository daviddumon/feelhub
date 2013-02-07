define([], function () {

    var container = "#feeling_form";
    var create_feeling_api_end_point = "/api/feelings";

    function init() {
        $(container + " button").click(function () {
            $(container + " .hidden-form").css("display", "inline");
        });

        setSubmitBehavior();
        setSelectedLanguage();
    }

    function setSelectedLanguage() {
        $("#feeling_form select option[value=" + languageCode + "]").prop('selected', true);
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

    function success() {
        document.location.reload(true);
    }

    function error() {
    }

    return {
        init: init
    };
});