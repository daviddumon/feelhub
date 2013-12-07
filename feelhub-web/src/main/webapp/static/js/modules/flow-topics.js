define(["jquery", "view/topic-view"], function ($, view) {

    var container = $("#flow");
    var skip, limit, hasData, notLoading, oldHeight;

    $(window).scroll(function () {
        draw_data();
    });

    $("#flow").on("flow-reset", function () {
        $("#flow").empty();
        init();
    });

    function init() {
        oldHeight = 0;
        skip = 0;
        limit = 50;
        hasData = true;
        notLoading = true;
        draw_data();
    }

    function draw_data() {
        if (need_data() && hasData && notLoading) {
            notLoading = false;
            load_data();
        }

        function load_data() {
            oldHeight = $(document).height();
            var parameters = [];
            var uri = root + "/api/topics/" + flow_uri_end_point + "?&skip=" + skip + "&limit=" + limit;

            $.getJSON(uri, function (data) {
                if (data.length > 0) {
                    skip += limit;
                    view.render_multiple(data, container);

                    if (data.length != limit) {
                        hasData = false;
                    }

                    setTimeout(function () {
                        if (need_data() && hasData) {
                            skip += limit;
                            load_data();
                        } else {
                            notLoading = true;
                        }
                    }, 200);
                }
            });
        }

        function need_data() {
            if ($(document).height() == oldHeight) {
                return false;
            }
            var docHeight = $(document).height();
            var scrollTop = $(window).scrollTop();
            var trigger = $(window).height() * 3;
            return (docHeight - scrollTop) < trigger;
        }
    }

    return {
        init: init
    };
});