define(["jquery", "view/flow/list-view"], function ($, list_view) {

    var container = $("#flow");
    var row_container = "#flow_list";
    var end_function, parameter, data_view, doit, api_end_point, datas, initial, skip, limit, maxBox, hasData, notLoading, basePollTime, lastFeelingId;
    var topicId = topicData.id;

    function init(end_point, param, view, callback) {
        doInit(end_point, param, view, callback);
        add_responsive_behavior();
        drawData();
        $(window).scroll(function () {
            drawData();
        });
    }

    function doInit(end_point, param, view, callback) {
        api_end_point = end_point;
        data_view = view;
        parameter = param;
        end_function = callback;
        initial = 320;
        maxBox = Math.floor(container.innerWidth() / initial);
        skip = -20;
        limit = 20;
        hasData = true;
        notLoading = true;
        for (var i = 0; i < maxBox; i++) {
            list_view.render(container, i);
        }
        datas = [];
        basePollTime = 60000;
    }

    function add_responsive_behavior() {
        $(window).resize(function () {
            clearTimeout(doit);
            doit = setTimeout(function () {
                endOfResize();
            }, 200);
        });

        $(window).on("resize", function () {
            clearTimeout(doit);
            doit = setTimeout(function () {
                endOfResize();
            }, 200);
        });

        $(window).on("orientationchange", function () {
            clearTimeout(doit);
            doit = setTimeout(function () {
                endOfResize();
            }, 200);
        });

        function endOfResize() {
            reset();
        }
    }

    function drawData() {

        if (needData() && hasData && notLoading) {
            notLoading = false;
            skip += limit;
            loadData();
        }

        function loadData() {
            var parameters = [];
            var uri = api_end_point + "?";
            if (parameter) {
                parameters.push({"value": parameter});
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
                        appendData(data);
                    });

                    //if (skip == 0) {
                    //    lastFeelingId = data[0].id;
                    //    poll(basePollTime);
                    //}

                    if (data.length != limit) {
                        hasData = false;
                    }

                    setTimeout(function () {
                        if (needData() && hasData) {
                            skip += limit;
                            loadData();
                        } else {
                            notLoading = true;
                        }
                    }, 200);
                }

                if (end_function) {
                    end_function();
                }
            });
        }

        function needData() {
            var docHeight = container.height();
            var scrollTop = $(window).scrollTop();
            var trigger = $(window).height() * 3;
            return (docHeight - scrollTop) < trigger;
        }
    }

    function appendData(data) {
        var row_index = 0;
        var row_height = $(row_container + "_" + row_index).height();
        for (var i = 1; i < maxBox; i++) {
            var current_height = $(row_container + "_" + i).height()
            if (current_height < row_height) {
                row_index = i;
                row_height = current_height;
            }
        }

        data_view.render(data, row_container + "_" + row_index);
        datas.push(data);
    }

    //function poll(time) {
    //    clearInterval(pollNewFeelings);
    //
    //    var pollNewFeelings = setInterval(function () {
    //        var parameters = [];
    //        var uri = root + "/api/newfeelings";
    //        if (topicId.length > 0) {
    //            parameters.push({"value": "topicId=" + encodeURIComponent(topicId)});
    //        }
    //        if (lastFeelingId) {
    //            parameters.push({"value": "lastFeelingId=" + lastFeelingId});
    //        }
    //        if (parameters.length > 0) {
    //            uri += "?";
    //            $.each(parameters, function (index, parameter) {
    //                uri += parameter.value + "&";
    //            });
    //            uri = uri.substr(0, uri.length - 1);
    //        }
    //
    //        $.getJSON(uri, function (data) {
    //
    //            if (data.length > 0) {
    //                lastFeelingId = data[0].id;
    //                data.reverse();
    //                $.each(data, function (index, feeling) {
    //                    var element = getFeeling(feeling, "feeling");
    //                    datas.unshift(element);
    //                });
    //                reset();
    //                poll(basePollTime);
    //            }
    //        })
    //            .error(function () {
    //                clearInterval(pollNewFeelings);
    //            });
    //    }, time);
    //}

    function reset() {
        container.empty();
        maxBox = Math.floor(container.innerWidth() / initial);
        for (var i = 0; i < maxBox; i++) {
            list_view.render(container, i);
        }
        data_view.reset();
        reDraw();
    }

    function reDraw() {
        $.each(datas, function (index, data) {
            var row_index = 0;
            var row_height = $(row_container + "_" + row_index).height();
            for (var i = 1; i < maxBox; i++) {
                var current_height = $(row_container + "_" + i).height()
                if (current_height < row_height) {
                    row_index = i;
                    row_height = current_height;
                }
            }

            data_view.render(data, row_container + "_" + row_index);
        });
    }

    return {
        init: init,
        reset: reset
    };
});