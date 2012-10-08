/* Copyright Steambeat 2012 */
$(function () {
    console.log("loading form");
    $("#form_submit").click(function (event) {
        event.stopImmediatePropagation();
        event.preventDefault();
        postOpinion();
    });
});

function postOpinion() {
    console.log("posting opinion");
    var judgments = [
        {"feeling":"good", "keywordValue":keywordValue, "languageCode":languageCode}
    ];
    var opinionData = {
        "text":"ceci est un test",
        "judgments":judgments
    };
    console.log(opinionData);
    $.ajax({
        url:root + '/json/createopinion',
        type:'POST',
        contentType:'application/json',
        data:JSON.stringify(opinionData),
        processData:false
    });
}