/* Copyright Steambeat 2012 */
function Flow() {
    var THIS = this;
    this.id = 1;
    this.container = $("#opinions");
    this.cssIndex = this.findStyleSheetIndex("flow.css");
    this.width = this.numericalValueFrom(this.extractValueFromCSS(".opinion", "width"));
    this.padding = this.numericalValueFrom(this.extractValueFromCSS(".opinion", "padding"));
    this.margin = this.numericalValueFrom(this.extractValueFromCSS(".opinion", "margin"));
    this.border = this.numericalValueFrom(this.extractValueFromCSS(".opinion", "border-width"));
    this.lines = new Array();
    this.freeLines = new Array();

    this.initial = this.getInitialWidth();

    this.leftCorner = this.setLeftCorner();
    this.skip = -20;
    this.limit = 20;
    this.hasData = true;
    this.notLoading = true;

    this.maxBox = this.getMaxBox();

    this.columns = new Array(this.maxBox);
    for (var i = 0; i < this.maxBox; i++) {
        this.columns[i] = 0;
    }

    THIS.drawData();

    $(window).scroll(function () {
        THIS.drawData();
    });
}

Flow.prototype.drawData = function () {
    var THIS = this;

    if (needData() && this.hasData && this.notLoading) {
        this.notLoading = false;
        THIS.skip += THIS.limit;
        loadData();
    }

    function loadData() {
        var subjectParameter = (typeof subjectId === 'undefined') ? "" : ("&subjectId=" + encodeURIComponent(subjectId));
        $.getJSON(root + "/opinions?skip=" + THIS.skip + "&limit=" + THIS.limit + subjectParameter, function (data) {
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
        var trigger = $(window).height() * 2;
        return (docHeight - scrollTop) < trigger;
    }
};

Flow.prototype.drawBox = function (opinion, classes) {
    var THIS = this;
    var opinionData = THIS.getOpinionData(opinion, classes);
    var element = ich.opinion(opinionData);
    if ($(window).width() > 720) {
        THIS.appendBehavior(element);
    }
    this.container.append(element);

    element.find(".judgments").css("width", element.width());

    var column = 0;
    var column_height = this.columns[0];
    for (var i = 1; i < this.columns.length; i++) {
        if (this.columns[i] < column_height) {
            column_height = this.columns[i];
            column = i;
        }
    }
    element.css("top", column_height);
    element.css("left", this.leftCorner + this.initial * column);

    this.columns[column] += (element.height() + 2 * (this.margin + this.padding));

    var max = this.columns[0];
    for (var i = 1; i < this.columns.length; i++) {
        if (this.columns[i] > column_height) {
            max = this.columns[i];
        }
    }

    this.container.css("height", max + this.margin);
};

Flow.prototype.getOpinionData = function (opinion, classes) {
    var id = "opinion_" + this.id++;

    var judgmentsData = [
        {
            feeling:opinion.feeling,
            uri:root+opinion.uriToken+opinion.subjectId,
            shortDescription:opinion.shortDescription,
            description:opinion.description
        }
    ];

    var opinionData = {
        id:id,
        classes:classes,
        texts:opinion.text.split(/\r\n|\r|\n/),
        judgments:judgmentsData
    };

    return opinionData;
};

Flow.prototype.appendBehavior = function (element) {
    element.find(".judgment_tag").mouseover(function (event) {
        var info = $(this).parent("div").find(".judgment_info");
        info.css("top", event.pageY - 60 - $(window).scrollTop());
        info.css("left", event.pageX - (info.width() / 2));
        info.show();
    });

    element.find(".judgment_tag").mouseout(function (event) {
        $(this).parent("div").find(".judgment_info").hide();
    });
};

Flow.prototype.numericalValueFrom = function (value) {
    if (value == "" || value == null) {
        return 0;
    } else if (value.substring(value.length - 1, value.length) == '%') {
        return parseInt(value.substring(0, value.length - 1)) / 100;
    } else {
        return parseInt(value.substring(0, value.length - 2));
    }
};

Flow.prototype.findStyleSheetIndex = function (cssSheet) {
    var styleSheets = document.styleSheets;
    for (var i = 0; i < styleSheets.length; i++) {
        if (extractCssSheetName(styleSheets[i].href) == cssSheet) {
            return i;
        }
    }

    function extractCssSheetName(styleSheet) {
        var buildTimeStringLength = 14;
        var styleName = styleSheet.substring(styleSheet.length - cssSheet.length - buildTimeStringLength, styleSheet.length - buildTimeStringLength);
        return styleName;
    }
};

Flow.prototype.extractValueFromCSS = function (className, property) {
    if (document.styleSheets[this.cssIndex] != undefined) {
        var rules = document.styleSheets[this.cssIndex].rules || document.styleSheets[this.cssIndex].cssRules;
        for (var i = 0; i < rules.length; i++) {
            if (rules[i].selectorText == className) {
                var value = rules[i].style.getPropertyValue(property);
                if (value != "" && value != null) {
                    return value;
                } else {
                    return "";
                }
            }
        }
    }
};

Flow.prototype.getInitialWidth = function () {
    var result = (this.width + 2 * (this.padding + this.margin + this.border));
    return Math.round(result);
};

Flow.prototype.getMaxBox = function () {
    var webpageWidth = this.container.innerWidth();
    var maxBox = Math.floor((webpageWidth) / this.initial);
    return maxBox;
};

Flow.prototype.setLeftCorner = function () {
    var availableSpace = this.container.innerWidth();
    var maxBox = Math.floor(availableSpace / this.initial);
    var usedSpace = maxBox * this.initial;
    return (availableSpace - usedSpace) / 2;
};

Flow.prototype.reset = function () {
    this.skip = -20;
    this.limit = 20;

    this.maxBox = this.getMaxBox();
    this.leftCorner = this.setLeftCorner();

    this.columns = new Array(this.maxBox);
    for (var i = 0; i < this.maxBox; i++) {
        this.columns[i] = 0;
    }

    this.drawData();
};