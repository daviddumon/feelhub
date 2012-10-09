/* Copyright Steambeat 2012 */
$(function () {
    if (referenceId !== "") {
        var flow = new Flow();

        var reference_data = {
            referenceId:referenceId,
            keywordValue:keywordValue,
            languageCode:languageCode,
            //illustrationLink:illustrationLink,
            classes:"reference_big reference_center"
        };

        $("#main_reference").prepend(ich.reference(reference_data));
        $("#" + referenceId + " img").attr("src", illustrationLink);
        $("#counters").show();

        RequestRelations(referenceId);
        RequestCounters(referenceId);
    }
});