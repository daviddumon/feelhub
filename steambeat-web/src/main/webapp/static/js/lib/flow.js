/* Copyright Steambeat 2012 */
function Flow() {
    this.initialize();
    this.drawData();
    $(window).scroll(function () {
        this.drawData();
    });
}

Flow.prototype.initialize = function () {
    var THIS = this;

    THIS.container = $("#opinions");
    THIS.initial = 300;
    THIS.maxBox = Math.floor(THIS.container.innerWidth() / THIS.initial);
    THIS.leftCorner = (THIS.container.innerWidth() - (THIS.maxBox * THIS.initial)) / 2;
    THIS.id = 1;
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
//                THIS.drawBox(opinion, "opinion");
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
//    var opinionData = THIS.getOpinionData(opinion, classes);
//    var element = ich.opinion(opinionData);
//    if ($(window).width() > 720) {
//        THIS.appendBehavior(element);
//    }
//    this.container.append(element);
//
//    element.find(".judgments").css("width", element.width());
//
//    var column = 0;
//    var column_height = this.columns[0];
//    for (var i = 1; i < this.columns.length; i++) {
//        if (this.columns[i] < column_height) {
//            column_height = this.columns[i];
//            column = i;
//        }
//    }
//    element.css("top", column_height);
//    element.css("left", this.leftCorner + this.initial * column);
//
//    this.columns[column] += (element.height() + 2 * (this.margin + this.padding));
//
//    var max = this.columns[0];
//    for (var i = 1; i < this.columns.length; i++) {
//        if (this.columns[i] > column_height) {
//            max = this.columns[i];
//        }
//    }
//
//    this.container.css("height", max + this.margin);
};

Flow.prototype.getOpinionData = function (opinion, classes) {
//    var id = "opinion_" + this.id++;
//
//    var judgmentsData = [
//        {
//            feeling:opinion.feeling,
//            uri:root + opinion.uriToken + opinion.subjectId,
//            shortDescription:opinion.shortDescription,
//            description:opinion.description
//        }
//    ];
//
//    var opinionData = {
//        id:id,
//        classes:classes,
//        texts:opinion.text.split(/\r\n|\r|\n/),
//        judgments:judgmentsData
//    };
//
//    return opinionData;
};

//Flow.prototype.appendBehavior = function (element) {
//    element.find(".judgment_tag").mouseover(function (event) {
//        var info = $(this).parent("div").find(".judgment_info");
//        info.css("top", event.pageY - 60 - $(window).scrollTop());
//        info.css("left", event.pageX - (info.width() / 2));
//        info.show();
//    });
//
//    element.find(".judgment_tag").mouseout(function (event) {
//        $(this).parent("div").find(".judgment_info").hide();
//    });
//};
//
//Flow.prototype.reset = function () {
//    this.skip = -30;
//    this.limit = 30;
//
//    this.maxBox = this.getMaxBox();
//    this.leftCorner = this.setLeftCorner();
//
//    this.columns = new Array(this.maxBox);
//    for (var i = 0; i < this.maxBox; i++) {
//        this.columns[i] = 0;
//    }
//
//    this.drawData();
//};