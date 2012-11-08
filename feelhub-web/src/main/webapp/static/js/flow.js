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
    THIS.hasData = (keywordValue.length > 0 && referenceId == "" ? false : true);
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
        if (referenceId.length > 0) {
            parameters.push({"value":"referenceId=" + encodeURIComponent(referenceId)});
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

    var referenceDatas = [];
    var known_references = {};
    for (var i = 0; i < feeling.referenceDatas.length; i++) {
        if (feeling.referenceDatas[i].referenceId !== referenceId) {
            var current = feeling.referenceDatas[i].referenceId;
            if (!(current in known_references)) {
                var reference_data = {
                    referenceId:feeling.referenceDatas[i].referenceId,
                    sentimentValue:feeling.referenceDatas[i].sentimentValue,
                    keywordValue:feeling.referenceDatas[i].keywordValue,
                    url:buildInternalLink(feeling.referenceDatas[i].languageCode, feeling.referenceDatas[i].keywordValue),
                    classes:"reference_medium reference_stack",
                    illustrationLink:feeling.referenceDatas[i].illustrationLink
                };
                referenceDatas.push(reference_data);
                known_references[current] = true;
            }
        } else {
            var feeling_sentiment_value = feeling.referenceDatas[i].sentimentValue;
        }
    }

    shuffleAndMakeFirstLarge();

    var feelingData = {
        id:feeling.id,
        feeling_classes:classes,
        text:text.split(/\r\n|\r|\n/),
        referenceDatas:referenceDatas,
        height:(referenceDatas.length != 0 ? 40 : 0) + 146 * (Math.floor(referenceDatas.length / 2) + referenceDatas.length % 2) + 'px'
    };

    if (feeling_sentiment_value !== "none") {
        feelingData["feeling_sentiment_value"] = feeling_sentiment_value;
        feelingData["feeling_sentiment_value_illustration"] = root + "/static/images/smiley_" + feeling_sentiment_value + "_white_14.png";
    }

    return ich.feeling(feelingData);

    function shuffleAndMakeFirstLarge() {
        if (referenceDatas.length % 2 != 0) {
            var shuffle_number = Math.floor(Math.random() * referenceDatas.length);
            for (var i = 0; i < shuffle_number; i++) {
                var rd = referenceDatas.shift();
                referenceDatas.push(rd);
            }
            referenceDatas[0]["classes"] = "reference_large reference_stack";
        }
    }
};

Flow.prototype.poll = function (time) {
    var THIS = this;
    clearInterval(THIS.pollNewFeelings);

    THIS.pollNewFeelings = setInterval(function () {
        var parameters = [];
        var uri = root + "/json/newfeelings";
        if (referenceId.length > 0) {
            parameters.push({"value":"referenceId=" + encodeURIComponent(referenceId)});
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

Flow.prototype.pushFake = function (referenceId, text, sentiment_value) {
    var THIS = this;
    //console.log("push fake : " + referenceId + " - " + text + " - " + sentiment_value);
    var fake_feeling = {
        id:referenceId,
        text:text,
        referenceDatas:[
            {referenceId:referenceId, sentimentValue:sentiment_value}
        ]
    };

    THIS.poll(500);

    // A finir : on met le fake, et on poll
    // Pour l'instant on poll juste comme un con

};