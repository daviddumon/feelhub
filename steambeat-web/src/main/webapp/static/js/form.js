/* Copyright Steambeat 2012 */
$(function () {
    console.log("loading form");
    $("#form input").click(function (event) {
        event.stopImmediatePropagation();
        event.preventDefault();
        postOpinion($(this).attr("value"), $("#form textarea").val());
    });
});

function postOpinion(feeling, text) {
    console.log("posting opinion " + feeling);
    var judgments = [];
    var main_judgment = {"feeling":feeling, "keywordValue":keywordValue, "languageCode":languageCode};
    judgments.push(main_judgment);
    addJudgments();
    var opinionData = {
        "text":text,
        "judgments":judgments
    };
    console.log(opinionData);
    $.ajax({
        url:root + '/json/createopinion',
        type:'POST',
        contentType:'application/json',
        data:JSON.stringify(opinionData),
        processData:false
    }).success(function () {
            window.location.reload();
        });

    function addJudgments() {
        var keywords = text.match(/[\#\+\-\=][^ ]+/g);
        if (keywords != null) {
            for (var i = 0; i < keywords.length; i++) {
                var keyword = keywords[i].replace(/[\#\+\-\=]/g, "");
                var feeling;
                switch (keywords[i].match(/[\#\+\-\=]/g)[0]) {
                    case '#':
                        feeling = "none";
                        break;
                    case '+':
                        feeling = "good";
                        break;
                    case '-':
                        feeling = "bad";
                        break;
                    case '=':
                        feeling = "neutral";
                        break;
                }
                judgments.push({"feeling":feeling, "keywordValue":keyword, "languageCode":languageCode});
            }
        }
    }
}