/* Copyright Steambeat 2012 */
function Flow() {
    var THIS = this;
    THIS.opinions = [];
    THIS.initialize();
    THIS.drawData();
    $(window).scroll(function () {
        THIS.drawData();
    });
}

Flow.prototype.initialize = function () {
    var THIS = this;
    THIS.container = $("#opinions");
    THIS.initial = 280;
    THIS.maxBox = Math.floor(THIS.container.innerWidth() / THIS.initial);
    THIS.skip = -30;
    THIS.limit = 30;
    THIS.hasData = true;
    THIS.notLoading = true;
    for (var i = 0; i < THIS.maxBox; i++) {
        THIS.container.append("<div class='opinion_list' id='opinion_list_" + i + "'></div>");
    }
    THIS.opinions = [];
};

Flow.prototype.drawData = function () {
    var THIS = this;

    if (needData() && THIS.hasData && THIS.notLoading) {
        THIS.notLoading = false;
        THIS.skip += THIS.limit;
        loadData();
    }

    function loadData() {
        var referenceParameter = "&referenceId=" + encodeURIComponent(referenceId);
        $.getJSON(root + "/json/opinions?skip=" + THIS.skip + "&limit=" + THIS.limit + referenceParameter + "&languageCode=" + languageCode, function (data) {
            $.each(data, function (index, opinion) {
                THIS.appendOpinion(opinion, "opinion");
            });

            if (THIS.skip == 0) {
                THIS.setLastOpinionId(data[0].id, 60000);
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
        });
    }

    function needData() {
        var docHeight = THIS.container.height();
        var scrollTop = $(window).scrollTop();
        var trigger = $(window).height() * 3;
        return (docHeight - scrollTop) < trigger;
    }
};

Flow.prototype.appendOpinion = function (opinion, classes) {
    var THIS = this;
    var element = THIS.getOpinion(opinion, classes);

    var row = 0;
    var row_height = $("#opinion_list_" + row).height();
    for (var i = 1; i < THIS.maxBox; i++) {
        var current_height = $("#opinion_list_" + i).height()
        if (current_height < row_height) {
            row = i;
            row_height = current_height;
        }
    }

    $("#opinion_list_" + row).append(element);
    THIS.opinions.push(element);
};

Flow.prototype.getOpinion = function (opinion, classes) {
    var THIS = this;
    var text = opinion.text.replace(/[\#\+\-\=][^ ]+/g, function (match) {
        match = match.replace(/[\#\+\-\=]/g, "");
        return "<span>" + match + "</span>";
    });
    text = text.replace(/[\#\+\-\=]+/g, "");

    var referenceDatas = [];
    var known_references = {};
    for (var i = 0; i < opinion.referenceDatas.length; i++) {
        if (opinion.referenceDatas[i].referenceId !== referenceId) {
            var current = opinion.referenceDatas[i].referenceId;
            if (!(current in known_references)) {
                var reference_data = {
                    referenceId:opinion.referenceDatas[i].referenceId,
                    feeling:opinion.referenceDatas[i].feeling,
                    keywordValue:opinion.referenceDatas[i].keywordValue,
                    url:buildInternalLink(opinion.referenceDatas[i].languageCode, opinion.referenceDatas[i].keywordValue),
                    classes:"reference_medium reference_stack reference_zoom",
                    illustrationLink:opinion.referenceDatas[i].illustrationLink
                };
                referenceDatas.push(reference_data);
                known_references[current] = true;
            }
        } else {
            var opinion_feeling = opinion.referenceDatas[i].feeling;
        }
    }

    shuffleAndMakeFirstLarge();

    var opinionData = {
        id:opinion.id,
        opinion_classes:classes,
        text:text.split(/\r\n|\r|\n/),
        referenceDatas:referenceDatas,
        height:(referenceDatas.length != 0 ? 40 : 0) + 74 * (Math.floor(referenceDatas.length / 2) + referenceDatas.length % 2) + 'px'
    };

    if (opinion_feeling !== "none") {
        opinionData["opinion_feeling"] = opinion_feeling;
        opinionData["opinion_feeling_illustration"] = root + "/static/images/smiley_" + opinion_feeling + "_white_14.png";
    }

    return ich.opinion(opinionData);

    function shuffleAndMakeFirstLarge() {
        if (referenceDatas.length % 2 != 0) {
            var shuffle_number = Math.floor(Math.random() * referenceDatas.length);
            for (var i = 0; i < shuffle_number; i++) {
                var rd = referenceDatas.shift();
                referenceDatas.push(rd);
            }
            referenceDatas[0]["classes"] = "reference_large reference_stack reference_zoom";
        }
    }
};

Flow.prototype.setLastOpinionId = function (lastOpinionId, time) {
    var THIS = this;
    clearInterval(THIS.pollNewOpinions);

    THIS.pollNewOpinions = setInterval(function () {
        console.log("request new opinions : " + referenceId + " - lastopinionid:" + lastOpinionId);
        $.getJSON(root + "/json/newopinions?referenceId=" + referenceId + "&lastOpinionId=" + lastOpinionId, function (data) {
            console.log(data);
            $.each(data, function (index, opinion) {
                THIS.prependOpinion(opinion, "opinion");
            });
        })
            .success(function () {
                console.log("succes poll new opinions");
            })
            .error(function () {
                console.log("error poll new opinions");
            });
    }, time);
};

Flow.prototype.reDraw = function (opinion, classes) {
    var THIS = this;
    $.each(THIS.opinions, function (index, element) {
        console.log("redraw opinion:" + element);

        var row = 0;
        var row_height = $("#opinion_list_" + row).height();
        for (var i = 1; i < THIS.maxBox; i++) {
            var current_height = $("#opinion_list_" + i).height()
            if (current_height < row_height) {
                row = i;
                row_height = current_height;
            }
        }

        $("#opinion_list_" + row).append(element);
    });
};

Flow.prototype.reset = function () {
    var THIS = this;
    THIS.container.empty();
    THIS.maxBox = Math.floor(THIS.container.innerWidth() / THIS.initial);
    for (var i = 0; i < THIS.maxBox; i++) {
        THIS.container.append("<div class='opinion_list' id='opinion_list_" + i + "'></div>");
    }
    THIS.reDraw();
};

Flow.prototype.pushFake = function (referenceId, text, feeling) {

    var fake_opinion = {
        referenceId:referenceId,
        text:text,
        referenceDatas:[
            {referenceId:referenceId, feeling:feeling}
        ]
    };

};