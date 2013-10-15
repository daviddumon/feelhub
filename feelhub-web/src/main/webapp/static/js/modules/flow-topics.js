define(["jquery", "view/topic-view"], function ($, view) {

    var container = $("#flow");
    var skip, limit, hasData, notLoading;

    function init() {
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
        $.each(initial_datas, function (index, data) {
            view.render(data, container);
        });
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
            var uri = root + "/api/topics/lastfeelings?&skip=" + skip + "&limit=" + limit;

            $.getJSON(uri, function (data) {
                if (data.length > 0) {
                    $.each(data, function (index, data) {
                        view.render(data, container);
                    });

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