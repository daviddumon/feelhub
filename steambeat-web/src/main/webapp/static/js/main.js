/* Copyright Steambeat 2012 */
$(function () {
    //var flow = new Flow();
    //var form = new Form();

    if (keyword.length != 0) {
        var reference_data = {
            referenceId:referenceId,
            keyword:keyword,
            width: 240,
            height: 160
        };

        $("#panel_center").append(ich.reference(reference_data));

        RequestIllustration(referenceId);
        RequestRelations(referenceId);
        RequestCounters(referenceId);
    }
});