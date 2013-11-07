define(["jquery", "modules/messages", "view/feeling-view", "modules/canvas"], function ($, messages, feeling_view, canvas) {

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
            $(this).css("opacity", 0);
            $(this).animate({opacity: 1}, 1000);
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

    function success(data, textStatus, xhr) {
        $(container + " textarea").val("");
        $(".help-text").show();
        feeling_view.prepend(data, "#feelings");
        messages.draw_message("good", "Your feeling has been posted!", 1);
        submitted = false;

        var good = parseInt($("#pie").data("good"));
        var bad = parseInt($("#pie").data("bad"));
        var neutral = parseInt($("#pie").data("neutral"));

        if(data.feelingValue == "good") {
            $("#pie").data("good", ++good);
        } else if (data.feelingValue == "bad") {
            $("#pie").data("bad", ++bad);
        } else {
            $("#pie").data("neutral", ++neutral);
        }

        var total = good + bad + neutral;

        if(total == 1) {
            $("#counter").text(total + " feeling");
        } else {
            $("#counter").text(total + " feelings");
        }

        canvas.pie("pie");
    }

    function error(jqXHR) {
        if (jqXHR.status == 401) {
            messages.draw_message("bad", "Please log in", 1);
            $("#login-button").click();
            submitted = false;
        }
    }

    return {
        init: init
    };

});