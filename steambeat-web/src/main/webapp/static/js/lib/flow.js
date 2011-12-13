/* Copyright bytedojo 2011 */
function Flow(cssSheet, containerName, className, loadButtonName) {
    var THIS = this;
    this.id = 1;
    this.container = $("#" + containerName);
    this.loadButton = $("#" + loadButtonName);
    this.cssIndex = this.findStyleSheetIndex(cssSheet);
    this.width = this.findValueFromCSS(className, "width");
    this.padding = this.findValueFromCSS(className, "padding");
    this.margin = this.findValueFromCSS(className, "margin");
    this.border = this.findValueFromCSS(className, "border-width");
    this.lines = new Array();
    this.freeLines = new Array();
    this.initial = this.getInitialWidth();
    this.maxBox = this.getMaxBox();
    this.leftCorner = this.setLeftCorner();
    this.topCorner = this.container.offset().top;
    this.skip = 0;
    this.limit = 50;

    this.drawData();
}
;

Flow.prototype.next = function () {
    this.skip += this.limit;
    this.drawData();
};

Flow.prototype.drawData = function () {
    var THIS = this;
    $.getJSON(root + "/opinions;" + this.skip + ";" + this.limit, function (data) {
        $("#loadmore_button").hide();
        $.each(data, function (index, opinion) {
            THIS.drawBox(opinion, "opinion shadow");
        });
        $("#loadmore_button").show();
    });
};

Flow.prototype.drawBox = function (opinion, classes) {
    var THIS = this;
    var id = "opinion_" + this.id++;

    var element = $("<li class='" + classes + "' id='" + id + "' style='position: absolute'>" + "</li>");
    element.append(opinion.text);
    var subjects = $("<div class='subjects'></div>");
    var subject = $("<div class='subject'></div>");

    var subjectTag = $("<span class='subject_tag " + opinion.feeling + "'>webpage</span>");
    subjectTag.mouseover(function (event) {
        var info = $(this).parent("div").find(".subject_info");
        info.css("top", event.pageY - 60 - $(window).scrollTop());
        info.css("left", event.pageX - (info.width() / 2));
        info.show();
    });
    subjectTag.mouseout(function (event) {
        $(this).parent("div").find(".subject_info").hide();
    });

    var subjectInfo = $("<span class='subject_info' style='display: none'>" + opinion.subjectId + "</span>");

    subject.append(subjectTag);
    subject.append(subjectInfo);
    subjects.append(subject);
    element.append(subjects);
    element.hide();
    this.container.append(element);
    setTimeout(function () {
        var boxSize = 1;
        console.log(element.width() + " " + element.height() + " " + element.outerWidth() + " " + element.innerWidth() + " " + THIS.initial);
        if (element.height() < element.width()) {
            element.css("height", element.width());
        } else {
            while (boxSize < THIS.maxBox && element.height() > element.width()) {
                boxSize++;
                element.css("width", THIS.findWidthForSize(boxSize));
            }
            element.css("height", element.width());
        }

        element.show();

        if (document.getElementById(id).scrollWidth > element.outerWidth()) {
            while (boxSize < THIS.maxBox && document.getElementById(id).scrollWidth > element.outerWidth()) {
                boxSize++;
                element.css("width", THIS.findWidthForSize(boxSize));
            }
            element.css("height", element.width());
        }

        var position = THIS.findNextFreeSpace(boxSize);
        THIS.putBox(position.line, position.index, boxSize);
        element.css("top", THIS.getTopPosition(position.line));
        element.css("left", THIS.getLeftPosition(position.index));
    }, 100);
};

Flow.prototype.findWidthForSize = function (size) {
    return (size * this.initial) - (2 * (this.numericalValueFrom(this.padding) + this.numericalValueFrom(this.margin) + this.numericalValueFrom(this.border)));
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
    this.setLoadButton();
};

Flow.prototype.setLoadButton = function () {
    this.loadButton.css("top", this.lines.length * (this.initial + this.numericalValueFrom(this.margin)) + parseInt(this.topCorner) + 80);
};

Flow.prototype.setCorrectHeightValue = function () {
    this.container.css("height", this.lines.length * (this.initial + this.numericalValueFrom(this.margin)) + 200);
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
    return this.initial * index;
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
        return styleSheet.substring(styleSheet.length - cssSheet.length, styleSheet.length);
    }
};

Flow.prototype.findValueFromCSS = function (className, property) {
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
    var opinionWidth = this.numericalValueFrom(this.width);
    var paddingwidth = this.numericalValueFrom(this.padding);
    var marginWidth = this.numericalValueFrom(this.margin);
    var borderWidth = this.numericalValueFrom(this.border);
    var result = (opinionWidth + 2 * (paddingwidth + marginWidth + borderWidth));
    console.log(result);
    return Math.round(result);
};

Flow.prototype.getMaxBox = function () {
    var webpageWidth = this.container.innerWidth();
    var result = Math.floor((webpageWidth - 180) / this.initial);
    this.setContainerWidth(result);
    return result;
};

Flow.prototype.setContainerWidth = function(numberOfBoxes) {
    this.container.css("width", numberOfBoxes * this.initial);
};

Flow.prototype.setLeftCorner = function () {
    var webpageWidth = this.container.innerWidth();
    var leftCorner = (webpageWidth - this.maxBox * this.initial - (this.maxBox + 1) * this.numericalValueFrom(this.margin)) / 2 + this.container.offset().left;
    return leftCorner;
};