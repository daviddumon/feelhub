define(["jquery"], function ($) {

    var container = "#feeling-form";

    function init() {
        setBehavior();
    }

    function setBehavior() {
        $(container + " textarea").keyup(function () {
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
            submit($(this).attr("feeling-value"));
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
            contentType: 'application/json',
            data: data,
            processData: false,
            dataType: 'json',
            success: success,
            error: error
        });
    }

    function success() {
        setTimeout(function () {
            document.location.reload(true);
        }, 1000);
    }

    function error() {
        console.log("error");
    }

    return {
        init: init
    };

});