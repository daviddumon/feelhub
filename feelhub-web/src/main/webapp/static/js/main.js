/* Copyright Feelhub 2012 */
$(function () {

    if (keywordValue !== "") {
        var keyword_data = {
            topicId:topicId,
            keywordValue:keywordValue,
            languageCode:languageCode,
            //illustrationLink:illustrationLink,
            classes:"keyword_main",
            typeValue:typeValue
        };

        $("#main_keyword").prepend(ich.keyword(keyword_data));
        $("#counters").show();
        $("#feeling_form").show();
    }

    if (topicId !== "") {
        FindInformations();
    }

    flow = new Flow();
});

function FindInformations() {
    $("#related").show();

    $("#" + topicId + " img").attr("src", illustrationLink);

    RequestRelations(topicId);
    RequestCounters(topicId);
}