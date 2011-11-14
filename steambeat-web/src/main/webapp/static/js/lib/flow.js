/* Copyright bytedojo 2011 */
function Flow(css, container, itemTag, className) {

    this.drawBox = function(text, classes) {
        var id = "opinion_" + this.id++;
        var opinion = document.createElement(itemTag);
        opinion.className = classes;
        opinion.setAttribute("id", id);
        opinion.style.position = "absolute";
        opinion.innerHTML = text;
        this.feed.appendChild(opinion);
        return id;
    };

    this.drawBoxLink = function(text, classes, link) {
        var id = "opinion_" + this.id++;
        var opinion = document.createElement(itemTag);
        opinion.className = classes;
        opinion.setAttribute("id", id);
        opinion.style.position = "absolute";
        opinion.innerHTML = text;
        var opinionlink = document.createElement("a");
        opinionlink.setAttribute("href", link);
        opinionlink.appendChild(opinion);
        opinionlink.className = "opinionlink";
        this.feed.appendChild(opinionlink);
        return id;
    };

    this.compute = function(id) {
        var element = $("#" + id);
        var boxSize = 1;
        if(element.height() < element.width()) {
            element.css("height", element.width());
        } else {
            while(boxSize < this.maxBox && element.height() > element.width()) {
                element.css("width", this.findNextWidth(element.innerWidth()));
                boxSize++;
            }
            element.css("height", element.width());
        }
        var position = this.findNextFreeSpace(boxSize);
        this.putBox(position.line, position.index, boxSize);
        element.css("top", this.getTopPosition(position.line));
        element.css("left", this.getLeftPosition(position.index));
    };

    this.findNextWidth = function(actual) {
        return actual + this.numericalValueFrom(this.margin) + this.initial - 2 * this.numericalValueFrom(this.padding);
    };

    this.getTopPosition = function(line) {
        return (this.initial + this.numericalValueFrom(this.margin)) * line;
    };

    this.getLeftPosition = function(index) {
        return this.leftCorner + (this.initial + this.numericalValueFrom(this.margin)) * index;
    };

    this.putBox = function(line, index, size) {
        for(var i = line; i < line + size; i++) {
            if(this.lines[i] == null) { this.createLine();};
            for(var j = index; j < index + size; j++ ){
                this.lines[i][j] = 1;
            }
            this.checkForFullLine(line);
        }
    };

    this.createLine = function() {
        var line = new Array();
        for(var i = 0; i < this.maxBox; i++) {
            line.push(0);
        }
        this.lines.push(line);
        this.freeLines.push(1);
        this.increaseFooter();
    };

    this.increaseFooter = function() {
        this.footerTop += (this.initial + this.numericalValueFrom(this.margin));
        $("footer").css("top", this.footerTop);
    };

    this.checkForFullLine = function(line) {
        var index = 0;
        while(this.lines[line][index] == 1) {
            index++;
        }
        if(index == this.maxBox) {
            this.freeLines[line] = 0;
        }
    };

    this.findNextFreeSpace = function(size) {
        var position = { index: 0, line: this.lines.length > 0 ? this.lines.length : 0};
        for(var line = 0; line < this.lines.length; line++) {
            if(this.freeLines[line] == 1) {
                for(var index = 0; index <= this.lines[line].length - size; index++) {
                    if(this.isBlockFree(line, index, 1)) {
                        if(this.testForSquare(line, index, size)) {
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

    this.testForSquare = function(line, index, size) {
        var isSquare = this.isBlockFree(line, index, size);
        var i = 1;
        while(isSquare) {
            if(this.lines[++line] != null && i < size) {
                isSquare = this.isBlockFree(line, index, size);
                i++;
            } else {
                break;
            }
        }
        return isSquare;
    };

    this.isBlockFree = function(line, index, size) {
        var free = true;
        if(size < 0 || size > this.maxBox) { return false; };
        for(var i = index; i < index + size; i++) {
            if(this.lines[line][i] == 1) {
                free = false;
                break;
            }
        }
        return free;
    };

    this.numericalValueFrom = function(value) {
        if(value == "" || value == null) {
            return 0;
        } else if(value.substring(value.length -1, value.length) == '%') {
            return parseInt(value.substring(0, value.length - 1)) / 100;
        } else {
            return parseInt(value.substring(0, value.length - 2));
        }
    };

    this.findStyleSheetIndex = function() {
        var styleSheets = document.styleSheets;
        for(var i = 0; i < styleSheets.length; i++) {
            var styleSheet = styleSheets[i].href;
            if(styleSheet.substring(styleSheet.length - css.length, styleSheet.length) == css) {
                return i;
            }
        }
    };

    this.findValueFromCSS = function(property) {
        if(document.styleSheets[this.cssIndex] != undefined) {
            var rules = document.styleSheets[this.cssIndex].rules || document.styleSheets[this.cssIndex].cssRules;
            for(var i = 0; i < rules.length ; i++) {
                if(rules[i].selectorText == className) {
                    var value = rules[i].style.getPropertyValue(property);
                    if(value != "" && value != null ) {
                        return value;
                    } else {
                        return "";
                    }
                }
            }
        }
    };

    this.setMaxBox = function() {
        var feedWidth = parseInt($("#" + container).innerWidth());
        var result = Math.floor(feedWidth / (this.initial + this.numericalValueFrom(this.margin)));
        return result;
    };

    this.setInitialWidth = function() {
        var feedwidth = parseInt($("#" + container).innerWidth());
        var liwidth = this.numericalValueFrom(this.width);
        var paddingwidth = this.numericalValueFrom(this.padding);
        var result = feedwidth * liwidth + 2 * paddingwidth;
        return Math.round(result);
    };

    this.setLeftCorner = function() {
        var feedWidth = $("#" + container).innerWidth();
        var leftCorner = (feedWidth - this.maxBox * this.initial - (this.maxBox + 1) * this.numericalValueFrom(this.margin)) / 2;
        return leftCorner;
    };

    this.setFooterTop = function() {
        var margin = $("#" + container).css("margin-top");
        return this.numericalValueFrom(margin);
    };

    this.cssIndex = this.findStyleSheetIndex();
    this.feed = document.getElementById(container);
    this.width = this.findValueFromCSS("width");
    this.padding = this.findValueFromCSS("padding");
    this.margin = this.findValueFromCSS("margin");
    this.id = 1;
    this.lines = new Array();
    this.freeLines = new Array();
    this.initial = this.setInitialWidth();
    this.maxBox = this.setMaxBox();
    this.leftCorner = this.setLeftCorner();
    this.footerTop = this.setFooterTop();
};
