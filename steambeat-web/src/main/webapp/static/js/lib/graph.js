/* Copyright Steambeat 2012 */
function Block(interval) {
    this.interval = interval;
    this.timeLabel;
    this.signLabel = {};

    this.coords = {
        good: {
            top: 0, bottom: 0
        },
        bad: {
            top: 0, bottom: 0
        },
        neutral: {
            top: 0, bottom: 0
        },
    };
};

function Graph(startInterval, endInterval, blockWidth, stroke, height) {

    this.path = function(type, x, y) {
        var path = "M" + parseInt(x + this.blockWidth) + "," + parseInt(y + graph.blocks[0].coords[type].bottom)
        + "L" + x + "," + parseInt(y + graph.blocks[0].coords[type].bottom)
        + "L" + x + "," + parseInt(y + graph.blocks[0].coords[type].top)
        + "L" + parseInt(x + this.blockWidth) + "," + parseInt(y + graph.blocks[0].coords[type].top);

        for(var i = 0 ; i < graph.blocks.length - 1; i++) {
            var bezierPoint = x + (this.blockWidth * (3 + i * 4)) / 2;

            path += "C" + bezierPoint + "," + parseInt(y + graph.blocks[i].coords[type].top)
            + "," + bezierPoint + "," + parseInt(y + graph.blocks[i+1].coords[type].top)
            + "," + parseInt(x + this.blockWidth * (i * 2 + 2)) + "," + parseInt(y + graph.blocks[i+1].coords[type].top)
            + "L" + parseInt(x + this.blockWidth * (i * 2 + 3)) + "," + parseInt(y + graph.blocks[i+1].coords[type].top);
        }

        path += "L" + parseInt(x + this.blockWidth * (graph.blocks.length * 2 - 1)) + "," + parseInt(y + graph.blocks[graph.blocks.length - 1].coords[type].bottom);

        for(var i = graph.blocks.length - 1 ; i > 0; i--) {
            var bezierPoint = x + (this.blockWidth * (4 * i - 1)) / 2;

            path += "L" + parseInt(x + this.blockWidth * (i * 2)) + "," + parseInt(y + graph.blocks[i].coords[type].bottom)
            + "C" + bezierPoint + "," + parseInt(y + graph.blocks[i].coords[type].bottom)
            + "," + bezierPoint + "," + parseInt(y + graph.blocks[i-1].coords[type].bottom)
            + "," + parseInt(x + this.blockWidth * (i * 2 - 1)) + "," + parseInt(y + graph.blocks[i-1].coords[type].bottom);
        }

        return path;
    };

    this.top = function(type, x, y) {
        var top = "M" + x + "," + parseInt(y + graph.blocks[0].coords[type].top)
        + "L" + parseInt(x + blockWidth) + "," + parseInt(y + graph.blocks[0].coords[type].top);

        for(var i = 0 ; i < graph.blocks.length - 1; i++) {
            var bezierPoint = x + (blockWidth * (3 + i * 4)) / 2;

            top += "C" + bezierPoint + "," + parseInt(y + graph.blocks[i].coords[type].top)
            + "," + bezierPoint + "," + parseInt(y + graph.blocks[i+1].coords[type].top)
            + "," + parseInt(x + blockWidth * (i * 2 + 2)) + "," + parseInt(y + graph.blocks[i+1].coords[type].top)
            + "L" + parseInt(x + blockWidth * (i * 2 + 3)) + "," + parseInt(y + graph.blocks[i+1].coords[type].top);
        }

        return top;
    };

    this.bottom = function(type, x, y) {
        var bottom = "M" + x + "," + parseInt(y + graph.blocks[0].coords[type].bottom)
        + "L" + parseInt(x + blockWidth) + "," + parseInt(y + graph.blocks[0].coords[type].bottom);

        for(var i = 0 ; i < graph.blocks.length - 1; i++) {
            var bezierPoint = x + (blockWidth * (3 + i * 4)) / 2;

            bottom += "C" + bezierPoint + "," + parseInt(y + graph.blocks[i].coords[type].bottom)
            + "," + bezierPoint + "," + parseInt(y + graph.blocks[i+1].coords[type].bottom)
            + "," + parseInt(x + blockWidth * (i * 2 + 2)) + "," + parseInt(y + graph.blocks[i+1].coords[type].bottom)
            + "L" + parseInt(x + blockWidth * (i * 2 + 3)) + "," + parseInt(y + graph.blocks[i+1].coords[type].bottom);
        }

        return bottom;
    };

    this.draw = function(svg, x, y, colors, callback) {
    
        $.each(timeline.types, function(index, type){
            var path = svg.path(graph.path(type, x, y));
            path.attr({stroke: colors[type].light,
                "fill-opacity": 1,
                "stroke-width": 0,
                fill: colors[type].light});

            var top = svg.path(graph.top(type, x, y));
            top.attr({stroke: colors[type].light,
                "fill-opacity": 1,
                "stroke-width": graph.stroke});

            var bottom = svg.path(graph.bottom(type, x, y));
            bottom.attr({stroke: colors[type].light,
                "fill-opacity": 1,
                "stroke-width": graph.stroke});

            path.toBack();
            top.toBack();
            bottom.toBack();

            graph.set.push(path);
            graph.set.push(top);
            graph.set.push(bottom);
        });

        $.each(graph.blocks, function(index, block){
            if(block.signLabel.text != "") {
                var sign = svg.text(x + (2 * index + 0.5) * graph.blockWidth, y + block.signLabel.y, block.signLabel.text);
                sign.attr({"fill": "#FFFFFF",
                    "font-size": 16,
                    "font-family": "Helvetica Neue"});
                graph.set.push(sign);
            }

            var time = svg.text(x + (2 * index + 0.5) * graph.blockWidth, y + parseInt(graph.height - 20 - graph.stroke), block.timeLabel);
            time.attr({"fill": "#7BAFD1",
                    "font-size": 12,
                    "font-family": "Helvetica Neue"});
            graph.set.push(time);
        });

        if(callback !== undefined && callback != null) {
            callback();
        }
    };

    this.remove = function() {
        $.each(this.set, function(index, element){
            element.remove();
        });
    };

    this.equals = function(otherGraph) {
        return this.startInterval.equals(otherGraph.startInterval)
            && this.endInterval.equals(otherGraph.endInterval);
    };

    this.setBlocks = function(blocks) {
        this.blocks = blocks;
    };

    this.animate = function(coords, delay, type) {
        $.each(graph.set, function(index, element){
            element.animate({translation: coords}, delay, (type != null ? type : ">"));
        });
    };

    this.translate = function(dx) {
        $.each(graph.set, function(index, element){
            element.translate(dx, 0);
        });
    };

    this.blockWidth = (blockWidth !== undefined ? blockWidth : 0);
    this.stroke = (stroke !== undefined ? stroke : 0);
    this.height = (height !== undefined ? height : 0);
    this.startInterval = startInterval;
    this.endInterval = endInterval;
    this.set = new Array();
    this.blocks = null;
    var graph = this;
};