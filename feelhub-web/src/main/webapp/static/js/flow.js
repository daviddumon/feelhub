/* Copyright Feelhub 2012 */
function Flow() {
    var THIS = this;
    THIS.feelings = [];
    THIS.initialize();
    THIS.drawData();
    $(window).scroll(function () {
        THIS.drawData();
    });
}

Flow.prototype.initialize = function () {
    var THIS = this;
    THIS.container = $("#feelings");
    THIS.initial = 320;
    THIS.maxBox = Math.floor(THIS.container.innerWidth() / THIS.initial);
    THIS.skip = -30;
    THIS.limit = 30;
    THIS.hasData = (keywordValue.length > 0 && topicId == "" ? false : true);
    THIS.notLoading = true;
    for (var i = 0; i < THIS.maxBox; i++) {
        THIS.container.append("<div class='feeling_list' id='feeling_list_" + i + "'></div>");
    }
    THIS.feelings = [];
    THIS.basePollTime = 60000;
};

Flow.prototype.drawData = function () {
    var THIS = this;

    if (needData() && THIS.hasData && THIS.notLoading) {
        THIS.notLoading = false;
        THIS.skip += THIS.limit;
        loadData();
    }

    function loadData() {
        var parameters = [];
        var uri = root + "/json/feelings?";
        if (topicId.length > 0) {
            parameters.push({"value":"topicId=" + encodeURIComponent(topicId)});
        }
        if (typeof userLanguageCode !== 'undefined') {
            parameters.push({"value":"languageCode=" + userLanguageCode});
        } else if (languageCode !== "none") {
            parameters.push({"value":"languageCode=" + languageCode});
        } else {
            parameters.push({"value":"languageCode=en"});
        }
        parameters.push({"value":"skip=" + THIS.skip});
        parameters.push({"value":"limit=" + THIS.limit});
        $.each(parameters, function (index, parameter) {
            uri += parameter.value + "&";
        });
        uri = uri.substr(0, uri.length - 1);
        //console.log(uri);
        $.getJSON(uri, function (data) {
            if (data.length > 0) {
                $.each(data, function (index, feeling) {
                    THIS.appendFeeling(feeling, "feeling");
                });

                if (THIS.skip == 0) {
                    THIS.lastFeelingId = data[0].id;
                    THIS.poll(THIS.basePollTime);
                }

                if (data.length != THIS.limit) {
                    THIS.hasData = false;
                }

                setTimeout(function () {
                    if (needData() && THIS.hasData) {
                        THIS.skip += THIS.limit;
                        loadData();
                    } else {
                        THIS.notLoading = true;
                    }
                }, 200);
            }
        });
    }

    function needData() {
        var docHeight = THIS.container.height();
        var scrollTop = $(window).scrollTop();
        var trigger = $(window).height() * 3;
        return (docHeight - scrollTop) < trigger;
    }
};

Flow.prototype.appendFeeling = function (feeling, classes) {
    var THIS = this;
    var element = THIS.getFeeling(feeling, classes);

    var row = 0;
    var row_height = $("#feeling_list_" + row).height();
    for (var i = 1; i < THIS.maxBox; i++) {
        var current_height = $("#feeling_list_" + i).height()
        if (current_height < row_height) {
            row = i;
            row_height = current_height;
        }
    }

    $("#feeling_list_" + row).append(element);
    THIS.feelings.push(element);
};

Flow.prototype.getFeeling = function (feeling, classes) {
    var THIS = this;
    var text = feeling.text.replace(/[\#\+\-\=][^ ]+/g, function (match) {
        match = match.replace(/[\#\+\-\=]/g, "");
        return "<span>" + match + "</span>";
    });
    text = text.replace(/[\#\+\-\=]+/g, "");

    var topicDatas = [];
    var known_topics = {};
    for (var i = 0; i < feeling.topicDatas.length; i++) {
        if (feeling.topicDatas[i].topicId !== topicId) {
            var current = feeling.topicDatas[i].topicId;
            if (!(current in known_topics)) {
                var topic_data = {
                    topicId:feeling.topicDatas[i].topicId,
                    sentimentValue:feeling.topicDatas[i].sentimentValue,
                    keywordValue:feeling.topicDatas[i].keywordValue,
                    url:buildInternalLink(feeling.topicDatas[i].languageCode, feeling.topicDatas[i].keywordValue),
                    classes:"topic_medium topic_stack",
                    illustrationLink:feeling.topicDatas[i].illustrationLink
                };
                topicDatas.push(topic_data);
                known_topics[current] = true;
            }
        } else {
            var feeling_sentiment_value = feeling.topicDatas[i].sentimentValue;
        }
    }

    shuffleAndMakeFirstLarge();

    var feelingData = {
        id:feeling.id,
        feeling_classes:classes,
        text:text.split(/\r\n|\r|\n/),
        topicDatas:topicDatas,
        height:(topicDatas.length != 0 ? 40 : 0) + 146 * (Math.floor(topicDatas.length / 2) + topicDatas.length % 2) + 'px'
    };

    if (feeling_sentiment_value !== "none") {
        feelingData["feeling_sentiment_value"] = feeling_sentiment_value;
        feelingData["feeling_sentiment_value_illustration"] = root + "/static/images/smiley_" + feeling_sentiment_value + "_white_14.png";
    }

    return ich.feeling(feelingData);

    function shuffleAndMakeFirstLarge() {
        if (topicDatas.length % 2 != 0) {
            var shuffle_number = Math.floor(Math.random() * topicDatas.length);
            for (var i = 0; i < shuffle_number; i++) {
                var rd = topicDatas.shift();
                topicDatas.push(rd);
            }
            topicDatas[0]["classes"] = "topic_large topic_stack";
        }
    }
};

Flow.prototype.poll = function (time) {
    var THIS = this;
    clearInterval(THIS.pollNewFeelings);

    THIS.pollNewFeelings = setInterval(function () {
        var parameters = [];
        var uri = root + "/json/newfeelings";
        if (topicId.length > 0) {
            parameters.push({"value":"topicId=" + encodeURIComponent(topicId)});
        }
        if (THIS.lastFeelingId) {
            parameters.push({"value":"lastFeelingId=" + THIS.lastFeelingId});
        }
        if (parameters.length > 0) {
            uri += "?";
            $.each(parameters, function (index, parameter) {
                uri += parameter.value + "&";
            });
            uri = uri.substr(0, uri.length - 1);
        }
        //console.log(uri);
        $.getJSON(uri, function (data) {
            //console.log(data);
            if (data.length > 0) {
                THIS.lastFeelingId = data[0].id;
                data.reverse();
                $.each(data, function (index, feeling) {
                    var element = THIS.getFeeling(feeling, "feeling");
                    THIS.feelings.unshift(element);
                });
                THIS.reset();
                THIS.poll(THIS.basePollTime);
            }
        })
            .error(function () {
                clearInterval(THIS.pollNewFeelings);
            });
    }, time);
};

Flow.prototype.reDraw = function (feeling, classes) {
    //console.log("redraw flow");
    var THIS = this;
    $.each(THIS.feelings, function (index, element) {
        var row = 0;
        var row_height = $("#feeling_list_" + row).height();
        for (var i = 1; i < THIS.maxBox; i++) {
            var current_height = $("#feeling_list_" + i).height()
            if (current_height < row_height) {
                row = i;
                row_height = current_height;
            }
        }

        $("#feeling_list_" + row).append(element);
    });
};

Flow.prototype.reset = function () {
    var THIS = this;
    THIS.container.empty();
    THIS.maxBox = Math.floor(THIS.container.innerWidth() / THIS.initial);
    for (var i = 0; i < THIS.maxBox; i++) {
        THIS.container.append("<div class='feeling_list' id='feeling_list_" + i + "'></div>");
    }
    THIS.reDraw();
};

Flow.prototype.pushFake = function (topicId, text, sentiment_value) {
    var THIS = this;
    //console.log("push fake : " + topicId + " - " + text + " - " + sentiment_value);
    var fake_feeling = {
        id:topicId,
        text:text,
        topicDatas:[
            {topicId:topicId, sentimentValue:sentiment_value}
        ]
    };

    THIS.poll(500);

    // A finir : on met le fake, et on poll
    // Pour l'instant on poll juste comme un con

};