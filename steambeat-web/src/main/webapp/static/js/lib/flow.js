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
    THIS.initial = 300;
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
    var opinionData = THIS.getOpinionData(opinion, classes);
    var element = ich.opinion(opinionData);
    THIS.container.append(element);

//    element.find(".judgments").css("width", element.width());

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

    THIS.container.css("height", max + THIS.margin);
};

Flow.prototype.getOpinionData = function (opinion, classes) {
    var THIS = this;
    var id = "opinion_" + THIS.id++;
    var text = opinion.text.replace(/[\#\+\-\=][^ ]+/g, function(match) {
        match = match.replace(/[\#\+\-\=]/g, "");
        return "<span class='reference'>" + match + "</span>";
    });

//    var judgmentsData = [
//        {
//            feeling:opinion.feeling,
//            uri:root + opinion.uriToken + opinion.subjectId,
//            shortDescription:opinion.shortDescription,
//            description:opinion.description
//        }
//    ];

    var opinionData = {
        id:id,
        classes:classes,
        text:text.split(/\r\n|\r|\n/),
//        judgments:judgmentsData
    };

    return opinionData;
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