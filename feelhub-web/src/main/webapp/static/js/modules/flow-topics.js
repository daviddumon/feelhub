define(["jquery", "view/topic-view"], function ($, view) {

    var container = $("#flow");
    var skip, limit, hasData, notLoading;

    function init() {
        skip = -10;
        limit = 30;
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
            //load_data();
        }

        function load_data() {
            var parameters = [];
            var uri = root + "/api/topics?";
            parameters.push({"value": "skip=" + skip});
            parameters.push({"value": "limit=" + limit});
            $.each(parameters, function (index, parameter) {
                uri += parameter.value + "&";
            });
            uri = uri.substr(0, uri.length - 1);

            $.getJSON(uri, function (data) {
                if (data.length > 0) {
                    $.each(data, function (index, data) {
                        view.render(data, row_container + "_" + row_index, box_width);
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
            var docHeight = container.height();
            var scrollTop = $(window).scrollTop();
            var trigger = $(window).height() * 3;
            return (docHeight - scrollTop) < trigger;
        }
    }

    return {
        init: init
    };
});