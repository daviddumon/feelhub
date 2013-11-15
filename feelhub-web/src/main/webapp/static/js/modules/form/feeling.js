define(["jquery", "modules/messages", "view/feeling-view", "modules/canvas"], function ($, messages, feeling_view, canvas) {

    var container = "#feeling-form";
    var submitted = false;

    function init() {

        $(container + " canvas").click(function (event) {
            event.stopImmediatePropagation();
            event.preventDefault();
            $(this).css("opacity", 0.5);
            $(this).animate({opacity: 1}, 200);
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
        feeling_view.render(data, "#feelings", true);
        messages.draw_message("happy", "Your feeling has been posted!", 1);
        submitted = false;

        var happy = parseInt($("#pie").data("happy"));
        var sad = parseInt($("#pie").data("sad"));
        var bored = parseInt($("#pie").data("bored"));

        if(data.feelingValue == "happy") {
            $("#pie").data("happy", ++happy);
        } else if (data.feelingValue == "sad") {
            $("#pie").data("sad", ++sad);
        } else {
            $("#pie").data("bored", ++bored);
        }

        var total = happy + sad + bored;

        if(total == 1) {
            $("#counter").text(total + " feeling");
        } else {
            $("#counter").text(total + " feelings");
        }

        canvas.pie("pie");
    }

    function error(jqXHR) {
        if (jqXHR.status == 401) {
            messages.draw_message("sad", "Please log in", 1);
            $("#login-button").click();
            submitted = false;
        }
    }

    return {
        init: init
    };

});