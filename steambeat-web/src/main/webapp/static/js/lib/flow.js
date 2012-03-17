/* Copyright bytedojo 2011 */
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
    this.maxBox = this.getMaxBox();
    this.leftCorner = this.setLeftCorner();
    this.skip = -20;
    this.limit = 20;
    this.hasData = true;
    this.notLoading = true;

    THIS.drawData();

    $(window).scroll(function () {
        console.log("scroll");
        THIS.drawData();
    });
}

Flow.prototype.drawData = function () {
    var THIS = this;

    if (needData() && this.hasData && this.notLoading) {
        console.log("need " + this.notLoading);
        this.notLoading = false;
        THIS.skip += THIS.limit;
        loadData();
    }

    function loadData() {
        var subjectParameter = (typeof subjectId === 'undefined') ? "" : ("&subjectId=" + encodeURIComponent(subjectId));
        $.getJSON(root + "/opinions?skip=" + THIS.skip + "&limit=" + THIS.limit + subjectParameter, function (data) {
            console.log("data " + THIS.notLoading);
            $.each(data, function (index, opinion) {
                THIS.drawBox(opinion, "opinion");
            });

            if (data.length != THIS.limit) {
                THIS.hasData = false;
            }

            setTimeout(function () {
                if (needData() && THIS.hasData) {
                    console.log("more " + THIS.notLoading);
                    THIS.skip += THIS.limit;
                    loadData();
                } else {
                    console.log("end");
                    THIS.notLoading = true;
                }
            }, 200);
        });
    }

    function needData() {
        var docHeight = THIS.container.height();
        var scrollTop = $(window).scrollTop();
        var trigger = $(window).height() * 1.5;
        return (docHeight - scrollTop) < trigger;
    }
};

Flow.prototype.drawBox = function (opinion, classes) {
    var THIS = this;
    var opinionData = THIS.getOpinionData(opinion, classes);
    var element = ich.opinion(opinionData);
    THIS.appendBehavior(element);
    this.container.append(element);

    setTimeout(function () {
        var boxSize = 1;
        if (element.height() < element.width()) {
            element.css("height", element.width());
        } else {
            while (boxSize < THIS.maxBox / 2 && element.height() > element.width()) {
                boxSize++;
                element.css("width", THIS.findWidthForSize(boxSize));
            }
            element.css("height", element.width());
        }

        element.show();

        if (document.getElementById(opinionData.id).scrollWidth > element.outerWidth()) {
            while (boxSize < THIS.maxBox / 2 && document.getElementById(opinionData.id).scrollWidth > element.outerWidth()) {
                boxSize++;
                element.css("width", THIS.findWidthForSize(boxSize));
            }
            element.css("height", element.width());
        }

        var position = THIS.findNextFreeSpace(boxSize);
        THIS.putBox(position.line, position.index, boxSize);
        THIS.setPositionsForElement(element, position);
    }, 150);
};

Flow.prototype.getOpinionData = function (opinion, classes) {
    var id = "opinion_" + this.id++;

    var judgmentsData = [
        {
            feeling:opinion.feeling,
            root:root,
            subjectId:opinion.subjectId,
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

Flow.prototype.setPositionsForElement = function (element, position) {
    element.css("top", this.getTopPosition(position.line));
    element.css("left", this.getLeftPosition(position.index));
    element.find(".judgments").css("position", "absolute");
    element.find(".judgments").css("bottom", "20px");
    element.find(".judgments").css("width", element.width());
};

Flow.prototype.findWidthForSize = function (size) {
    return (size * this.initial) - (2 * (this.padding + this.margin + this.border));
};

Flow.prototype.findNextFreeSpace = function (size) {
    var position = { index:0, line:this.lines.length > 0 ? this.lines.length : 0};
    for (var line = 0; line < this.lines.length; line++) {
        if (this.freeLines[line] == 1) {
            for (var index = 0; index <= this.lines[line].length - size; index++) {
                if (this.isBlockFree(line, index, 1)) {
                    if (this.testForSquare(line, index, size)) {
                        position.line = line;
                        position.index = index;
                        return position;
                    }
                }
            }
        }
    }
    return position;
};

Flow.prototype.testForSquare = function (line, index, size) {
    var isSquare = this.isBlockFree(line, index, size);
    var i = 1;
    while (isSquare) {
        if (this.lines[++line] != null && i < size) {
            isSquare = this.isBlockFree(line, index, size);
            i++;
        } else {
            break;
        }
    }
    return isSquare;
};

Flow.prototype.isBlockFree = function (line, index, size) {
    var free = true;
    if (size < 0 || size > this.maxBox) {
        return false;
    }
    ;
    for (var i = index; i < index + size; i++) {
        if (this.lines[line][i] == 1) {
            free = false;
            break;
        }
    }
    return free;
};

Flow.prototype.putBox = function (line, index, size) {
    for (var i = line; i < line + size; i++) {
        if (this.lines[i] == null) {
            this.createLine();
        }
        ;
        for (var j = index; j < index + size; j++) {
            this.lines[i][j] = 1;
        }
        this.checkForFullLine(line);
    }
};

Flow.prototype.createLine = function () {
    var line = new Array();
    for (var i = 0; i < this.maxBox; i++) {
        line.push(0);
    }
    this.lines.push(line);
    this.freeLines.push(1);
    this.setCorrectHeightValue();
};

Flow.prototype.setCorrectHeightValue = function () {
    this.container.css("height", this.lines.length * this.initial + 100);
};

Flow.prototype.checkForFullLine = function (line) {
    var index = 0;
    while (this.lines[line][index] == 1) {
        index++;
    }
    if (index == this.maxBox) {
        this.freeLines[line] = 0;
    }
};

Flow.prototype.getTopPosition = function (line) {
    return this.initial * line;
};

Flow.prototype.getLeftPosition = function (index) {
    return this.leftCorner + this.initial * index;
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
        return styleSheet.substring(styleSheet.length - cssSheet.length - buildTimeStringLength, styleSheet.length - buildTimeStringLength);
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
    var leftCorner = (availableSpace - usedSpace) / 2;
    return leftCorner;
};