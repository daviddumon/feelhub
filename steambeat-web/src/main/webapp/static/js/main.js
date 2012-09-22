/* Copyright Steambeat 2012 */
$(function () {
    //var flow = new Flow();
    //var form = new Form();

    if (keyword.length != 0) {
        var reference_data = {
            referenceId:referenceId,
            keyword:keyword,
            classes:"reference_big reference_center"
        };

        $("#panel_center").prepend(ich.reference(reference_data));

        RequestIllustration(referenceId);
        RequestRelations(referenceId);
        RequestCounters(referenceId);
    }
});