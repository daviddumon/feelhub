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

    THIS.drawData();

    $(window).scroll(function () {
        THIS.drawData();
    });
}

Flow.prototype.drawData = function () {
    var THIS = this;
    if (needData() && this.hasData) {
        THIS.skip += THIS.limit;
        loadData();
    }

    function needData() {
        var docHeight = THIS.container.height();
        var scrollTop = $(window).scrollTop();
        var trigger = $(window).height() * 1.5;
        return (docHeight - scrollTop) < trigger;
    }

    function loadData() {
        var subjectParameter = (typeof subjectId === 'undefined') ? "" : ("&subjectId=" + encodeURIComponent(subjectId));
        $.getJSON(root + "/opinions?skip=" + THIS.skip + "&limit=" + THIS.limit + subjectParameter , function (data) {
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
                }
            }, 200);
        });
    }
};

Flow.prototype.drawBox = function (opinion, classes) {
    var THIS = this;
    var id = "opinion_" + this.id++;

    var element = $("<li class='" + classes + "' id='" + id + "' style='position: absolute'><p>" + opinion.text + "</p></li>");
    var subjects = $("<div class='subjects'></div>");
    var subject = $("<div class='subject'></div>");
    var subjectHeader = $("<div class='subjects_header font_title'>related</div>");

    var subjectTag = $("<a class='subject_tag font_title " + opinion.feeling + "' href='" + root + "/webpages/" + opinion.subjectId + "'>" + opinion.shortDescription + "</a>");
    subjectTag.mouseover(function (event) {
        var info = $(this).parent("div").find(".subject_info");
        info.css("top", event.pageY - 60 - $(window).scrollTop());
        info.css("left", event.pageX - (info.width() / 2));
        info.show();
    });
    subjectTag.mouseout(function (event) {
        $(this).parent("div").find(".subject_info").hide();
    });

    var subjectInfo = $("<span class='subject_info font_title'>" + opinion.subjectId + "</span>");

    subjects.append(subjectHeader);
    subject.append(subjectTag);
    subject.append(subjectInfo);
    subjects.append(subject);
    element.append(subjects);
    element.hide();
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

        if (document.getElementById(id).scrollWidth > element.outerWidth()) {
            while (boxSize < THIS.maxBox / 2 && document.getElementById(id).scrollWidth > element.outerWidth()) {
                boxSize++;
                element.css("width", THIS.findWidthForSize(boxSize));
            }
            element.css("height", element.width());
        }

        var position = THIS.findNextFreeSpace(boxSize);
        THIS.putBox(position.line, position.index, boxSize);
        element.css("top", THIS.getTopPosition(position.line));
        element.css("left", THIS.getLeftPosition(position.index));
        subjects.css("position", "absolute");
        subjects.css("bottom", "20px");
        subjects.css("width", element.width());
    }, 200);
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