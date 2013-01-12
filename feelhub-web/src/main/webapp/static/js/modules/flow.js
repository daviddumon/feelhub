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
        skip = -30;
        limit = 30;
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
            }, 100);
        });

        $(window).on("orientationchange", function () {
            clearTimeout(doit);
            doit = setTimeout(function () {
                endOfResize();
            }, 100);
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
            api_end_point += "?";
            if(parameter) {
                parameters.push({"value": parameter});
            }
            parameters.push({"value": "skip=" + skip});
            parameters.push({"value": "limit=" + limit});
            $.each(parameters, function (index, parameter) {
                api_end_point += parameter.value + "&";
            });
            api_end_point = api_end_point.substr(0, api_end_point.length - 1);
            
            $.getJSON(api_end_point, function (data) {
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

                end_function();
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
        //var element = getFeeling(data);

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

        //$(row_container + "_" + row_index).append(element);
        //datas.push(element);
    }

    //function getFeeling(feeling) {
    //    var classes = "feeling";
    //    var text = feeling.text.replace(/[\#\+\-\=][^ ]+/g, function (match) {
    //        match = match.replace(/[\#\+\-\=]/g, "");
    //        return "<span>" + match + "</span>";
    //    });
    //    text = text.replace(/[\#\+\-\=]+/g, "");
    //
    //    var keywordDatas = [];
    //    var known_topics = {};
    //    for (var i = 0; i < feeling.keywordDatas.length; i++) {
    //        if (feeling.keywordDatas[i].topicId !== topicId) {
    //            var current = feeling.keywordDatas[i].topicId;
    //            if (!(current in known_topics)) {
    //                var keyword_data = {
    //                    topicId: feeling.keywordDatas[i].topicId,
    //                    sentimentValue: feeling.keywordDatas[i].sentimentValue,
    //                    keywordValue: feeling.keywordDatas[i].keywordValue,
    //                    //url: buildInternalLink(feeling.keywordDatas[i].typeValue, feeling.keywordDatas[i].languageCode, feeling.keywordDatas[i].keywordValue),
    //                    classes: "keyword_medium keyword_stack",
    //                    illustrationLink: feeling.keywordDatas[i].illustrationLink
    //                };
    //                keywordDatas.push(keyword_data);
    //                known_topics[current] = true;
    //            }
    //        } else {
    //            var feeling_sentiment_value = feeling.keywordDatas[i].sentimentValue;
    //        }
    //    }
    //
    //    shuffleAndMakeFirstLarge();
    //
    //    var feelingData = {
    //        id: feeling.id,
    //        feeling_classes: classes,
    //        text: text.split(/\r\n|\r|\n/),
    //        keywordDatas: keywordDatas,
    //        height: (keywordDatas.length != 0 ? 40 : 0) + 146 * (Math.floor(keywordDatas.length / 2) + keywordDatas.length % 2) + 'px'
    //    };
    //
    //    if (feeling_sentiment_value !== "none") {
    //        feelingData["feeling_sentiment_value"] = feeling_sentiment_value;
    //        feelingData["feeling_sentiment_value_illustration"] = root + "/static/images/smiley_" + feeling_sentiment_value + "_white_14.png";
    //    }
    //
    //    return ich.feeling(feelingData);
    //
    //    function shuffleAndMakeFirstLarge() {
    //        if (keywordDatas.length % 2 != 0) {
    //            var shuffle_number = Math.floor(Math.random() * keywordDatas.length);
    //            for (var i = 0; i < shuffle_number; i++) {
    //                var rd = keywordDatas.shift();
    //                keywordDatas.push(rd);
    //            }
    //            keywordDatas[0]["classes"] = "keyword_large keyword_stack";
    //        }
    //    }
    //}

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

            //$(row_container + "_" + row).append(data);
            data_view.render(data, row_container + "_" + row_index);
        });
    }

    return {
        init: init,
        reset: reset
    };
});