define(["jquery", "view/feeling-view"], function ($, view) {

    var container = "#feelings";
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
            view.render(data, container, false);
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
            var uri = root + "/api/topic/" + topicData.id + "/feelings?skip=" + skip + "&limit=" + limit;

            $.getJSON(uri, function (data) {
                if (data.length > 0) {
                    $.each(data, function (index, data) {
                        view.render(data, container, false);
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
            var trigger = $(window).height() * 2;
            return (docHeight - scrollTop) < trigger;
        }
    }

    return {
        init: init
    }
});