/* Copyright Feelhub 2012 */
$(function () {

    if (keywordValue !== "") {
        var topic_data = {
            topicId:topicId,
            keywordValue:keywordValue,
            languageCode:languageCode,
            //illustrationLink:illustrationLink,
            classes:"topic_main"
        };

        $("#main_topic").prepend(ich.topic(topic_data));
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