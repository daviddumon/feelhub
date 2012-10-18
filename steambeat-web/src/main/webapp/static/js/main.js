/* Copyright Steambeat 2012 */
$(function () {

    flow = new Flow();

    if (keywordValue !== "") {
        var reference_data = {
            referenceId:referenceId,
            keywordValue:keywordValue,
            languageCode:languageCode,
            //illustrationLink:illustrationLink,
            classes:"reference_big reference_center"
        };

        $("#main_reference").prepend(ich.reference(reference_data));
        $("#counters").show();
        $("#counters").show();
    }

    if (referenceId !== "") {
        FindInformations(referenceId);
    }
});

function FindInformations(referenceId) {
    $("#related").show();

    $("#" + referenceId + " img").attr("src", illustrationLink);

    RequestRelations(referenceId);
    RequestCounters(referenceId);
}