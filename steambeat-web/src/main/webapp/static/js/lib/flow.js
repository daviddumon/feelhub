/* Copyright Steambeat 2012 */
function Flow() {
    var THIS = this;
    THIS.initialize();
    THIS.drawData();
    $(window).scroll(function () {
        THIS.drawData();
    });
}

Flow.prototype.initialize = function () {
    var THIS = this;
    THIS.id = 1;
    THIS.container = $("#opinions");
    THIS.initial = 280;
    THIS.maxBox = Math.floor(THIS.container.innerWidth() / THIS.initial);
    THIS.leftCorner = (THIS.container.innerWidth() - (THIS.maxBox * THIS.initial)) / 2;
    THIS.skip = -30;
    THIS.limit = 30;
    THIS.hasData = true;
    THIS.notLoading = true;
    THIS.columns = new Array(THIS.maxBox);
    for (var i = 0; i < THIS.maxBox; i++) {
        THIS.columns[i] = 0;
    }
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
        $.getJSON(root + "/json/opinions?skip=" + THIS.skip + "&limit=" + THIS.limit + referenceParameter, function (data) {
            $.each(data, function (index, opinion) {
                THIS.drawBox(opinion, "opinion");
            });

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

Flow.prototype.drawBox = function (opinion, classes) {
    var THIS = this;
    var element = THIS.getOpinion(opinion, classes);
    THIS.container.append(element);

    var column = 0;
    var column_height = THIS.columns[0];
    for (var i = 1; i < THIS.columns.length; i++) {
        if (THIS.columns[i] < column_height) {
            column_height = THIS.columns[i];
            column = i;
        }
    }
    element.css("top", column_height);
    element.css("left", THIS.leftCorner + THIS.initial * column);

    THIS.columns[column] += (element.height() + 60);

    var max = THIS.columns[0];
    for (var i = 1; i < THIS.columns.length; i++) {
        if (THIS.columns[i] > column_height) {
            max = THIS.columns[i];
        }
    }

    THIS.container.css("height", max);
};

Flow.prototype.getOpinion = function (opinion, classes) {
    var THIS = this;
    var id = "opinion_" + THIS.id++;
    var text = opinion.text.replace(/[\#\+\-\=][^ ]+/g, function (match) {
        match = match.replace(/[\#\+\-\=]/g, "");
        return "<span>" + match + "</span>";
    });

    var referenceDatas = [];
    for (var i = 0; i < opinion.referenceDatas.length; i++) {
        if (opinion.referenceDatas[i].referenceId !== referenceId) {
            var reference_data = {
                referenceId:opinion.referenceDatas[i].referenceId,
                feeling:opinion.referenceDatas[i].feeling,
                keywordValue:opinion.referenceDatas[i].keywordValue,
                url:buildInternalLink(opinion.referenceDatas[i].languageCode, opinion.referenceDatas[i].keywordValue),
                classes:"reference_medium reference_stack reference_zoom",
                illustrationLink:opinion.referenceDatas[i].illustrationLink
            };
            referenceDatas.push(reference_data);
        }
    }

    shuffleAndMakeFirstLarge();

    var opinionData = {
        id:id,
        opinion_classes:classes,
        text:text.split(/\r\n|\r|\n/),
        referenceDatas:referenceDatas
    };

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

Flow.prototype.reset = function () {
    var THIS = this;
    THIS.skip = -30;
    THIS.limit = 30;

    THIS.maxBox = Math.floor(THIS.container.innerWidth() / THIS.initial);
    THIS.leftCorner = (THIS.container.innerWidth() - (THIS.maxBox * THIS.initial)) / 2;

    THIS.columns = new Array(THIS.maxBox);
    for (var i = 0; i < THIS.maxBox; i++) {
        this.columns[i] = 0;
    }

    THIS.drawData();
};