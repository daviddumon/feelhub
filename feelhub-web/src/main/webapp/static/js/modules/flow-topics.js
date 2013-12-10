define(["jquery", "view/topic-view", "modules/filters"], function ($, view, filters) {

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
        limit = 20;
        hasData = true;
        notLoading = true;
        draw_data();
    }

    function draw_data() {
        if (need_data() && hasData && notLoading) {
            notLoading = false;
            load_data();
        }

        function filters_query() {
            return "&order=" + filters.getFilter("filter-order") + "&from-people=" + filters.getFilter("filter-from-people")
        }

        function load_data() {
            oldHeight = $(document).height();
            var uri = root + "/api/topics/search?" + "skip=" + skip + "&limit=" + limit + filters_query();
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