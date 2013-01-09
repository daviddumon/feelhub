define(["jquery", "view/flow/list-view"], function ($, list_view) {

    //var doit;
    //    $(window).resize(function () {
    //        clearTimeout(doit);
    //        doit = setTimeout(function () {
    //            endOfResize();
    //        }, 100);
    //    });
    //
    //    $(window).on("orientationchange", function () {
    //        clearTimeout(doit);
    //        doit = setTimeout(function () {
    //            endOfResize();
    //        }, 100);
    //    });
    //
    //    function endOfResize() {
    //        if (typeof flow !== 'undefined') {
    //            flow.reset();
    //        }
    //    }
    //

    var container = $("#feelings");
    var feelings, initial, skip, limit, maxBox, hasData, notLoading, basePollTime, lastFeelingId;
    var topicId = topicData.id;

    function init() {
        doInit();
        drawData();
        $(window).scroll(function () {
            drawData();
        });
    }

    function doInit() {
        initial = 320;
        maxBox = Math.floor(container.innerWidth() / initial);
        skip = -30;
        limit = 30;
        hasData = true;
        notLoading = true;
        for (var i = 0; i < maxBox; i++) {
            list_view.render(container, i);
        }
        feelings = [];
        basePollTime = 60000;
    }

    function drawData() {

        if (needData() && hasData && notLoading) {
            notLoading = false;
            skip += limit;
            loadData();
        }

        function loadData() {
            var parameters = [];
            var uri = root + "/api";
            if (topicId.length > 0) {
                uri += "/topic/";
                uri += encodeURIComponent(topicId);
                uri += "/feelings";
            } else {
                uri += "/feelings";
            }
            uri += "?";
            parameters.push({"value": "skip=" + skip});
            parameters.push({"value": "limit=" + limit});
            $.each(parameters, function (index, parameter) {
                uri += parameter.value + "&";
            });
            uri = uri.substr(0, uri.length - 1);

            $.getJSON(uri, function (data) {
                if (data.length > 0) {
                    $.each(data, function (index, feeling) {
                        appendFeeling(feeling, "feeling");
                    });

                    if (skip == 0) {
                        lastFeelingId = data[0].id;
                        poll(basePollTime);
                    }

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
            });
        }

        function needData() {
            var docHeight = container.height();
            var scrollTop = $(window).scrollTop();
            var trigger = $(window).height() * 3;
            return (docHeight - scrollTop) < trigger;
        }
    }

    function appendFeeling(feeling, classes) {
        var element = getFeeling(feeling, classes);

        var row = 0;
        var row_height = $("#feeling_list_" + row).height();
        for (var i = 1; i < maxBox; i++) {
            var current_height = $("#feeling_list_" + i).height()
            if (current_height < row_height) {
                row = i;
                row_height = current_height;
            }
        }

        $("#feeling_list_" + row).append(element);
        feelings.push(element);
    }

    function getFeeling(feeling, classes) {
        var text = feeling.text.replace(/[\#\+\-\=][^ ]+/g, function (match) {
            match = match.replace(/[\#\+\-\=]/g, "");
            return "<span>" + match + "</span>";
        });
        text = text.replace(/[\#\+\-\=]+/g, "");

        var keywordDatas = [];
        var known_topics = {};
        for (var i = 0; i < feeling.keywordDatas.length; i++) {
            if (feeling.keywordDatas[i].topicId !== topicId) {
                var current = feeling.keywordDatas[i].topicId;
                if (!(current in known_topics)) {
                    var keyword_data = {
                        topicId: feeling.keywordDatas[i].topicId,
                        sentimentValue: feeling.keywordDatas[i].sentimentValue,
                        keywordValue: feeling.keywordDatas[i].keywordValue,
                        //url: buildInternalLink(feeling.keywordDatas[i].typeValue, feeling.keywordDatas[i].languageCode, feeling.keywordDatas[i].keywordValue),
                        classes: "keyword_medium keyword_stack",
                        illustrationLink: feeling.keywordDatas[i].illustrationLink
                    };
                    keywordDatas.push(keyword_data);
                    known_topics[current] = true;
                }
            } else {
                var feeling_sentiment_value = feeling.keywordDatas[i].sentimentValue;
            }
        }

        shuffleAndMakeFirstLarge();

        var feelingData = {
            id: feeling.id,
            feeling_classes: classes,
            text: text.split(/\r\n|\r|\n/),
            keywordDatas: keywordDatas,
            height: (keywordDatas.length != 0 ? 40 : 0) + 146 * (Math.floor(keywordDatas.length / 2) + keywordDatas.length % 2) + 'px'
        };

        if (feeling_sentiment_value !== "none") {
            feelingData["feeling_sentiment_value"] = feeling_sentiment_value;
            feelingData["feeling_sentiment_value_illustration"] = root + "/static/images/smiley_" + feeling_sentiment_value + "_white_14.png";
        }

        return ich.feeling(feelingData);

        function shuffleAndMakeFirstLarge() {
            if (keywordDatas.length % 2 != 0) {
                var shuffle_number = Math.floor(Math.random() * keywordDatas.length);
                for (var i = 0; i < shuffle_number; i++) {
                    var rd = keywordDatas.shift();
                    keywordDatas.push(rd);
                }
                keywordDatas[0]["classes"] = "keyword_large keyword_stack";
            }
        }
    }

    function poll(time) {
        clearInterval(pollNewFeelings);

        var pollNewFeelings = setInterval(function () {
            var parameters = [];
            var uri = root + "/api/newfeelings";
            if (topicId.length > 0) {
                parameters.push({"value": "topicId=" + encodeURIComponent(topicId)});
            }
            if (lastFeelingId) {
                parameters.push({"value": "lastFeelingId=" + lastFeelingId});
            }
            if (parameters.length > 0) {
                uri += "?";
                $.each(parameters, function (index, parameter) {
                    uri += parameter.value + "&";
                });
                uri = uri.substr(0, uri.length - 1);
            }

            $.getJSON(uri, function (data) {

                if (data.length > 0) {
                    lastFeelingId = data[0].id;
                    data.reverse();
                    $.each(data, function (index, feeling) {
                        var element = getFeeling(feeling, "feeling");
                        feelings.unshift(element);
                    });
                    reset();
                    poll(basePollTime);
                }
            })
                .error(function () {
                    clearInterval(pollNewFeelings);
                });
        }, time);
    }

    function reset() {
        container.empty();
        maxBox = Math.floor(container.innerWidth() / initial);
        for (var i = 0; i < maxBox; i++) {
            container.append("<div class='feeling_list' id='feeling_list_" + i + "'></div>");
        }
        reDraw();
    }

    function reDraw(feeling, classes) {
        $.each(feelings, function (index, element) {
            var row = 0;
            var row_height = $("#feeling_list_" + row).height();
            for (var i = 1; i < maxBox; i++) {
                var current_height = $("#feeling_list_" + i).height()
                if (current_height < row_height) {
                    row = i;
                    row_height = current_height;
                }
            }

            $("#feeling_list_" + row).append(element);
        });
    }

    return {
        init: init,
        reset: reset
    };
});