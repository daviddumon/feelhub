/* Copyright bytedojo 2011 */
$(function() {
    var flow = new Flow();
    flow.setAddOpinionButton();

    //var form = $("#post_opinion");
    //
    //$("#submit_good").click(postWith("good"));
    //$("#submit_bad").click(postWith("bad"));
    //$("#submit_neutral").click(postWith("neutral"));
    //
    //function postWith(feeling) {
    //    return function(e) {
    //        e.preventDefault();
    //        e.stopImmediatePropagation();
    //        doPost(feeling);
    //        return false;
    //    }
    //}
    //
    //function doPost(feeling) {
    //    var values = form.serializeArray();
    //    values.push({name : "feeling", value : feeling});
    //    $.post(form.attr("action"), values, function(data, textStatus, jqXHR) {
    //        location.href = jqXHR.getResponseHeader("Location");
    //    });
    //}
});