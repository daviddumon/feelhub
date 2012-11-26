/* Copyright Feelhub 2012 */
$(function () {
    $("#form").children().focus(function () {
        $("#form textarea").height("100px");
    });

    $("#form button").click(function (event) {
        event.stopImmediatePropagation();
        event.preventDefault();
        postFeeling($(this).attr("name"), $("#form textarea").val());
    });

    $("#createtopic input").click(function (event) {
        event.stopImmediatePropagation();
        event.preventDefault();
        $.ajax({
            url:root + '/api/topics',
            type:'POST',
            contentType:'application/json',
            data:JSON.stringify($("#createtopic").serializeArray()),
            processData:false,
            success:function (data, textStatus, jqXHR) {
                console.log("succes creation topic");
            },
            error:function () {
                console.log("erreur creation topic");
            }
        });
    });
});

function postFeeling(sentimentValue, text) {
    var feelingData = {
        "sentimentValue":sentimentValue,
        "text":text,
        "keywordValue":keywordValue,
        "languageCode":languageCode,
        "userLanguageCode":userLanguageCode
    };
    //console.log("ajax call");
    //console.log(feelingData);
    $.ajax({
        url:root + '/api/createfeeling',
        type:'POST',
        contentType:'application/json',
        data:JSON.stringify(feelingData),
        processData:false,
        success:function (data, textStatus, jqXHR) {
            $("#form textarea").val('');
            $("#form textarea").height("30px");
            if (topicId === "") {
                pollForId(data.id, text, sentimentValue);
            } else {
                flow.pushFake(data.id, text, sentimentValue);
            }
        },
        error:function () {
            //console.log("erreur lors du post");
        }
    });
}
;