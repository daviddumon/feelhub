define(["jquery", "view/topic-view"], function ($, view) {

    var container = $("#flow");
    var skip, limit, hasData, notLoading, oldHeight;

    function init() {
        oldHeight = $(document).height();
        skip = 0;
        limit = 50;
        hasData = true;
        notLoading = true;
        render_initial_datas();
        $(window).scroll(function () {
            draw_data();
        });
    }

    function render_initial_datas() {
        view.render_multiple(initial_datas, container);
        if (initial_datas.length != limit) {
            hasData = false;
        }
    }

    function draw_data() {
        if (need_data() && hasData && notLoading) {
            notLoading = false;
            skip += limit;
            load_data();
        }

        function load_data() {
            var parameters = [];
            var uri = root + "/api/topics/" + flow_uri_end_point + "?&skip=" + skip + "&limit=" + limit;

            $.getJSON(uri, function (data) {
                if (data.length > 0) {
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