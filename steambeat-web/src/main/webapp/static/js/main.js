/* Copyright Steambeat 2012 */
$(function () {
    //var flow = new Flow();
    var form = new Form();

    var reference_data = {
        referenceId:referenceId,
        keywordValue:keywordValue,
        languageCode:languageCode,
        //illustrationLink:illustrationLink,
        classes:"reference_big reference_center"
    };

    $("#panel_center").prepend(ich.reference(reference_data));
    $("#" + referenceId + " img").attr("src", illustrationLink);
    $("#counters").show();

    RequestRelations(referenceId);
    RequestCounters(referenceId);
});