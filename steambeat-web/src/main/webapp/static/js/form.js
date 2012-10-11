/* Copyright Steambeat 2012 */
$(function () {
    $("#form").children().focus(function () {
        $("#form textarea").height("100px");
    });

    $("#form button").click(function (event) {
        event.stopImmediatePropagation();
        event.preventDefault();
        postOpinion($(this).attr("name"), $("#form textarea").val());
    });
});

function postOpinion(feeling, text) {
    var opinionData = {
        "feeling":feeling,
        "text":text,
        "keywordValue":keywordValue,
        "languageCode":languageCode,
        "userLanguageCode":userLanguageCode
    };

    $.ajax({
        url:root + '/json/createopinion',
        type:'POST',
        contentType:'application/json',
        data:JSON.stringify(opinionData),
        processData:false
    }).success(function (data) {
            console.log(data);
            $("form textarea").empty();
        });
}