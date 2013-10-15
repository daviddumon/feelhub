define(["jquery", "modules/messages"], function ($, messages) {

    var container = "#feeling-form";
    var submitted = false;

    function init() {
        $(container + " textarea").focus(function () {
            $(this).parent().find(".help-text").hide();
        });

        $(container + " textarea").focusout(function () {
            if ($(this).val() == "") {
                $(this).parent().find(".help-text").show();
            }
        });

        $(".help-text").click(function () {
            $(container + " textarea").focus();
        });

        $(container + " canvas").click(function (event) {
            event.stopImmediatePropagation();
            event.preventDefault();
            if (!submitted) {
                submitted = true;
                submit($(this).attr("feeling-value"));
            }
        });
    }

    function submit(feelingValue) {
        var data = JSON.stringify({
            "text": $(container + " textarea").val(),
            "feelingValue": feelingValue,
            "topicId": topicData.id,
            "languageCode": languageCode});

        $.ajax({
            type: "POST",
            url: root + "/api/feelings",
            contentType: 'application/json; charset=UTF-8',
            data: data,
            processData: false,
            dataType: 'json',
            success: success,
            error: error
        });
    }

    function success() {
        setTimeout(function () {
            messages.store_message("good", "Your feeling has been posted!", 1);
            document.location.reload(true);
        }, 1000);
    }

    function error(jqXHR) {
        if (jqXHR.status == 401) {
            $("#login-button").click();
            submitted = false;
        }
    }

    return {
        init: init
    };

});