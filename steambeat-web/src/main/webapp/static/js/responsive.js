/* Copyright Steambeat 2012 */
$(function () {

    var doit;
    $(window).resize(function () {
        clearTimeout(doit);
        doit = setTimeout(function () {
            endOfResize();
        }, 100);
    });

    $(window).on("orientationchange", function () {
        clearTimeout(doit);
        doit = setTimeout(function () {
            endOfResize();
        }, 100);
    });

    function endOfResize() {
        if (typeof flow !== 'undefined') {
            flow.reset();
        }
    }
});
