define([], function () {

    var container = "#feeling_form";
    var create_feeling_api_end_point = "/api/feelings";

    function init() {
        $(container + " button").click(function () {
            $(container + " .hidden-form").show(400);
            $(container + " textarea").focus();
        });
        setTextareaBehavior();
        setSubmitBehavior();
        setSelectedLanguage();
    }

    function setTextareaBehavior() {
        $(container + " textarea").keypress(function (event) {
            $(this).parent().find(".help_text").hide();
            if (event.shiftKey) {
                var code = event.keyCode || event.which;
                if (code == 13) {
                    submit();
                    return false;
                }
            }
        });

        $(container + " textarea").focusout(function () {
            if ($(this).val() == "") {
                $(this).parent().find(".help_text").show();
            }
        });
    }

    function setSelectedLanguage() {
        $("#feeling_form select option[value=" + languageCode + "]").prop('selected', true);
    }

    function setSubmitBehavior() {
        $(container).submit(function () {
            submit();
            return false;
        });
    }

    function submit() {
        if ($("#feeling_form textarea").val() !== "") {
            $.ajax({
                url: root + create_feeling_api_end_point,
                type: 'POST',
                data: $(container).serialize(),
                success: success,
                error: error
            });
        }
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