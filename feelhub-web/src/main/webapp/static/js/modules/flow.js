define(["jquery", "view/flow/list-view"], function ($, list_view) {

    var container = $("#flow");
    var row_container = "#flow_list";
    var end_function, parameter, data_view, doit, api_end_point, datas, target_width, box_width, skip, limit, maxBox, hasData, notLoading, basePollTime, lastFeelingId, spacer;

    function init(end_point, param, view, callback) {
        do_init(end_point, param, view, callback);
        render_initial_datas();
        add_responsive_behavior();
        $(window).scroll(function () {
            draw_data();
        });
    }

    function render_list() {
        for (var i = 0; i < maxBox; i++) {
            list_view.render(container, i, box_width);
        }
    }

    function do_init(end_point, param, view, callback) {
        api_end_point = end_point;
        data_view = view;
        parameter = param;
        end_function = callback;
        compute_max_box();
        skip = -10;
        limit = 30;
        hasData = true;
        notLoading = true;
        render_list();
        datas = [];
        basePollTime = 60000;
    }

    function compute_max_box() {
        spacer = 20;
        var margin = 20;
        target_width = 584;
        maxBox = Math.ceil((container.innerWidth() - spacer) / target_width);
        box_width = ((container.innerWidth() - spacer) / maxBox) - margin;
        if (maxBox < 1) {
            maxBox = 1;
        }
    }

    function render_initial_datas() {
        $.each(initial_datas, function (index, data) {
            append_data(data);
        });
        if (initial_datas.length != 20) {
            hasData = false;
        }
        if (end_function) {
            end_function();
        }
    }

    function add_responsive_behavior() {
        $(window).on("resize", function () {
            clearTimeout(doit);
            doit = setTimeout(function () {
                end_of_resize();
            }, 200);
        });

        $(window).on("orientationchange", function () {
            clearTimeout(doit);
            doit = setTimeout(function () {
                end_of_resize();
            }, 200);
        });

        function end_of_resize() {
            reset();
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
            var uri = api_end_point + "?";
            if (parameter) {
                parameters.push({"value": "q=" + encodeURIComponent(parameter)});
            }
            parameters.push({"value": "skip=" + skip});
            parameters.push({"value": "limit=" + limit});
            $.each(parameters, function (index, parameter) {
                uri += parameter.value + "&";
            });
            uri = uri.substr(0, uri.length - 1);

            $.getJSON(uri, function (data) {
                if (data.length > 0) {
                    $.each(data, function (index, data) {
                        append_data(data);
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

    function draw_feeling(data) {
        var row_index = 0;
        var row_height = $(row_container + "_" + row_index).height();
        for (var i = 1; i < maxBox; i++) {
            var current_height = $(row_container + "_" + i).height()
            if (current_height < row_height) {
                row_index = i;
                row_height = current_height;
            }
        }

        data_view.render(data, row_container + "_" + row_index, box_width);
    }

    function append_data(data) {
        draw_feeling(data);
        datas.push(data);
    }

    function reset() {
        container.empty();
        compute_max_box();
        render_list();
        data_view.reset();
        re_draw();
    }

    function re_draw() {
        $.each(datas, function (index, data) {
            draw_feeling(data);
        });
    }

    return {
        init: init,
        reset: reset
    };
});