/* Copyright Steambeat 2012 */
$(function () {

    if (keywordValue !== "") {
        var reference_data = {
            referenceId:referenceId,
            keywordValue:keywordValue,
            languageCode:languageCode,
            //illustrationLink:illustrationLink,
            classes:"reference_main"
        };

        $("#main_reference").prepend(ich.reference(reference_data));
        $("#counters").show();
        $("#opinion_form").show();
    } else {
        $("#left").hide();
        $("#right").css("width", "100%");
    }

    if (referenceId !== "") {
        FindInformations();
    }

    flow = new Flow();
});

function FindInformations() {
    $("#related").show();

    $("#" + referenceId + " img").attr("src", illustrationLink);

    RequestRelations(referenceId);
    RequestCounters(referenceId);
}