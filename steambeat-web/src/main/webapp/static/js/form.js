/* Copyright Steambeat 2012 */
$(function () {
    $("#form input").click(function (event) {
        event.stopImmediatePropagation();
        event.preventDefault();
        postOpinion($(this).attr("value"), $("#form textarea").val());
    });
});

function postOpinion(feeling, text) {
    var opinionData = {
        "text":text,
        "feeling":feeling,
        "keywordValue":keywordValue,
        "languageCode":languageCode
    };

    $.ajax({
        url:root + '/json/createopinion',
        type:'POST',
        contentType:'application/json',
        data:JSON.stringify(opinionData),
        processData:false
    }).success(function () {

        });
}