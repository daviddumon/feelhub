/* Copyright Steambeat 2012 */
$(function () {
    //var flow = new Flow();
    //var form = new Form();

    var reference_data = {
        referenceId:referenceId,
        keyword:keyword,
        illustration:root + "/static/images/unknown.png",
        classes:""
    };

    $("#panel_center").append(ich.reference(reference_data));

    RequestIllustration(referenceId);
    RequestRelations(referenceId);
    RequestCounters(referenceId);
});