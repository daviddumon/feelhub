/* Copyright Feelhub 2012 */
var timeline = {};

(function ($) {

    var instance = this;

    this.init = function (granularity, baseSource, width, height, spacer, stroke, blockNumber, divName) {
        instance.granularity = granularity;
        instance.baseSource = baseSource;
        instance.displayWidth = width;
        instance.displayHeight = height;
        instance.spacer = spacer;
        instance.stroke = stroke;
        instance.blockNumber = blockNumber;
        instance.blockWidth = width / (instance.blockNumber * 2 - 1);
        instance.displayDiv = divName + "_display";
        instance.backDiv = divName + "_back";
        instance.forwardDiv = divName + "_forward";
        instance.upDiv = divName + "_up";
        instance.downDiv = divName + "_down";

        createDisplayPanel.call(this, instance.displayDiv, instance.displayWidth, instance.displayHeight);
        //createButtonsPanel.call(this, instance.displayWidth, instance.displayHeight);
        //createDragPanel.call(this, instance.displayWidth, instance.displayHeight);
    };

    function createDragPanel(width, height) {
        var rect = instance.displaySvg.rect(0, 0, width, height).attr({"fill": "#000000", "opacity": 0});
        rect.drag(function (dx, dy) {

            if (dx > 100) {
                //console.log("back");
                this.drag(null, null, null);
                instance.back();
            }
            if (dx < -100) {
                //console.log("forward");
                this.drag(null, null, null);
                instance.forward();
            }
        }, null, null);
    }

    ;

    this.translate = function (dx) {
        $.each(Date.granularities, function (index, granularity) {
            if (instance.graphs[granularity].length > 0
                && granularity == instance.granularity) {
                instance.graphs[granularity].previous().translate(dx, 0);
                instance.graphs[granularity].current().translate(dx, 0);
                instance.graphs[granularity].next().translate(dx, 0);
            }
        });
    };

    function createDisplayPanel(divName, width, height) {
        instance.displaySvg = Raphael(divName, width, height);
        this.displaySvg.clear();
    }

    ;

    function createButtonsPanel(width, height) {
        //this.backSvg = Raphael(instance.backDiv, width/10, height + 100);
        //this.forwardSvg = Raphael(instance.forwardDiv, width/10, height + 100);
        //this.upSvg = Raphael(instance.upDiv, width, 50);
        //this.downSvg = Raphael(instance.downDiv, width, 50);
        //this.backButton = this.backSvg.path("M50,40L20,140L50,240Z").attr({fill: "#555555"});
        //this.forwardButton = this.forwardSvg.path("M10,90L40,140L10,180Z").attr({fill: "#555555"});
        //this.upButton = this.upSvg.path("M200,40L300,10L400,40Z").attr({fill: "#555555"});
        //this.downButton = this.downSvg.path("M200,10L300,40L400,10Z").attr({fill: "#555555"});
    }

    ;

    this.loadInitialGraphs = function (x, y) {
        var now = new Date().interval(instance.granularity);

        start = now.previousInterval((instance.blockNumber * 2) - 1);
        end = now.previousInterval(instance.blockNumber);
        addGraph(start, end, x, y, -instance.displayWidth, 0);

        start = now.previousInterval(instance.blockNumber - 1);
        end = now;
        var current = addGraph(start, end, x, y, 0, 0);
        instance.graphs[instance.granularity].setCurrent(current);

        start = now.nextInterval(1);
        end = now.nextInterval(instance.blockNumber);
        addGraph(start, end, x, y, instance.displayWidth, 0);
    };

    function addGraph(start, end, x, y, offsetX, offsetY) {
        var graph = instance.buildGraph(start, end);
        var jqxhrCurrent = instance.loadBlocksFor(graph);
        jqxhrCurrent.complete(function () {
            graph.draw(instance.displaySvg, offsetX + x, offsetY + y, feelhub.colors, instance.checkAnimate);
        });
        instance.graphs[instance.granularity].add(graph);
        return graph;
    }

    this.back = function () {
        instance.nextAnimate = {
            coords: instance.displayWidth,
            delay: 1000,
            type: ">"
        };

        var graphToHide = instance.graphs[instance.granularity].next();
        instance.graphs[instance.granularity].setPreviousAsCurrentNode();

        if (instance.graphs[instance.granularity].previous() == null) {
            var current = instance.graphs[instance.granularity].current();
            var graph = instance.buildGraph(current.startInterval.previousInterval(5), current.endInterval.previousInterval(5));
            var jqxhr = instance.loadBlocksFor(graph);
            jqxhr.complete(function () {
                graph.draw(instance.displaySvg, -instance.displayWidth * 2, 0, feelhub.colors, instance.checkAnimate);
            });
            instance.graphs[instance.granularity].add(graph);
        } else {
            var graph = instance.graphs[instance.granularity].previous();
            graph.draw(instance.displaySvg, -instance.displayWidth * 2, 0, feelhub.colors, instance.animate);
        }
        graphToHide.remove();
    };

    this.forward = function () {
        instance.nextAnimate = {
            //coords: - instance.blockWidth * (instance.blockNumber - 1) / 2,
            coords: -instance.displayWidth,
            delay: 1000,
            type: ">"
        };

        var graphToHide = instance.graphs[instance.granularity].previous();
        instance.graphs[instance.granularity].setNextAsCurrentNode();

        if (instance.graphs[instance.granularity].next() == null) {
            var current = instance.graphs[instance.granularity].current();
            var graph = instance.buildGraph(current.startInterval.nextInterval(5), current.endInterval.nextInterval(5));
            var jqxhr = instance.loadBlocksFor(graph);
            jqxhr.complete(function () {
                graph.draw(instance.displaySvg, 1200, 0, feelhub.colors, instance.checkAnimate);
            });
            instance.graphs[instance.granularity].add(graph);
        } else {
            var graph = instance.graphs[instance.granularity].next();
            graph.draw(instance.displaySvg, 1200, 0, feelhub.colors, instance.animate);
        }
        graphToHide.remove();
    };

    this.up = function () {
        var newGranularity = Date.nextGranularity(timeline.granularity);

        if (newGranularity != timeline.granularity) {
            instance.nextAnimate = {
                coords: "0,250",
                delay: 500,
                type: ">"
            };

            var interval = instance.graphs[instance.granularity].current().startInterval.toInterval(newGranularity);

            var graph = instance.graphs[newGranularity].find(interval);

            if (graph == null) {

                start = interval.previousInterval(7);
                end = interval.previousInterval(3);
                var previous = instance.buildGraph(start, end);
                var jqxhrPrevious = instance.loadBlocksFor(previous);
                jqxhrPrevious.complete(function () {
                    previous.draw(instance.displaySvg, -600, -250, feelhub.colors, instance.checkAnimate);
                });
                instance.graphs[newGranularity].add(previous);

                start = interval.previousInterval(2);
                end = interval.nextInterval(2);
                var current = instance.buildGraph(start, end);
                var jqxhrCurrent = instance.loadBlocksFor(current);
                jqxhrCurrent.complete(function () {
                    current.draw(instance.displaySvg, 0, -250, feelhub.colors, instance.checkAnimate);
                });
                instance.graphs[newGranularity].add(current);

                instance.graphs[newGranularity].setCurrent(current);

                start = interval.nextInterval(3);
                end = interval.nextInterval(7);
                var next = instance.buildGraph(start, end);
                var jqxhrNext = instance.loadBlocksFor(next);
                jqxhrNext.complete(function () {
                    next.draw(instance.displaySvg, 600, -250, feelhub.colors, instance.checkAnimate);
                });
                instance.graphs[newGranularity].add(next);

            } else {

                instance.graphs[newGranularity].setCurrent(graph);
                graph.draw(instance.displaySvg, 0, -250, feelhub.colors, instance.animate);

                if (instance.graphs[newGranularity].previous() != null) {
                    instance.graphs[newGranularity].previous().draw(instance.displaySvg, -600, -250, feelhub.colors, instance.animate);
                } else {
                    // on charge previous
                }

                if (instance.graphs[newGranularity].next() != null) {
                    instance.graphs[newGranularity].next().draw(instance.displaySvg, 600, -250, feelhub.colors, instance.animate);
                } else {
                    // on charge next
                }
            }

            instance.granularityToRemove = instance.granularity;
            instance.granularity = newGranularity;
        } else {
            instance.nextAnimate = {
                coords: "0,30",
                delay: 300,
                type: ">"
            };
            instance.animate();
            setTimeout(function () {
                instance.nextAnimate = {
                    coords: "0,-30",
                    delay: 1500,
                    type: "bounce"
                };
                instance.animate();
            }, 300);
        }
    };

    this.down = function () {
        var newGranularity = Date.previousGranularity(timeline.granularity);

        if (newGranularity != timeline.granularity) {
            instance.nextAnimate = {
                coords: "0,-250",
                delay: 500,
                type: ">"
            };

            var interval = instance.graphs[instance.granularity].current().startInterval.toInterval(newGranularity);

            var graph = instance.graphs[newGranularity].find(interval);

            if (graph == null) {
                start = interval.previousInterval(7);
                end = interval.previousInterval(3);
                var previous = instance.buildGraph(start, end);
                var jqxhrPrevious = instance.loadBlocksFor(previous);
                jqxhrPrevious.complete(function () {
                    previous.draw(instance.displaySvg, -600, 250, feelhub.colors, instance.checkAnimate);
                });
                instance.graphs[newGranularity].add(previous);

                start = interval.previousInterval(2);
                end = interval.nextInterval(2);
                var current = instance.buildGraph(start, end);
                var jqxhrCurrent = instance.loadBlocksFor(current);
                jqxhrCurrent.complete(function () {
                    current.draw(instance.displaySvg, 0, 250, feelhub.colors, instance.checkAnimate);
                });
                instance.graphs[newGranularity].add(current);

                instance.graphs[newGranularity].setCurrent(current);

                start = interval.nextInterval(3);
                end = interval.nextInterval(7);
                var next = instance.buildGraph(start, end);
                var jqxhrNext = instance.loadBlocksFor(next);
                jqxhrNext.complete(function () {
                    next.draw(instance.displaySvg, 600, 250, feelhub.colors, instance.checkAnimate);
                });
                instance.graphs[newGranularity].add(next);

            } else {

                instance.graphs[newGranularity].setCurrent(graph);
                graph.draw(instance.displaySvg, 0, 250, feelhub.colors, instance.animate);

                if (instance.graphs[newGranularity].previous() != null) {
                    instance.graphs[newGranularity].previous().draw(instance.displaySvg, -600, 250, feelhub.colors, instance.animate);
                } else {
                    // on charge previous
                }

                if (instance.graphs[newGranularity].next() != null) {
                    instance.graphs[newGranularity].next().draw(instance.displaySvg, 600, 250, feelhub.colors, instance.animate);
                } else {
                    // on charge next
                }

            }

            instance.granularityToRemove = instance.granularity;
            instance.granularity = newGranularity;
        } else {
            instance.nextAnimate = {
                coords: "0,-30",
                delay: 300,
                type: ">"
            };
            instance.animate();
            setTimeout(function () {
                instance.nextAnimate = {
                    coords: "0,30",
                    delay: 1500,
                    type: "bounce"
                };
                instance.animate();
            }, 300);
        }
    };

    this.checkAnimate = function () {
        if (--instance.loadingCounter == 0) {
            instance.animate();
        }
    };

    this.animate = function () {
        $.each(Date.granularities, function (index, granularity) {
            if (instance.graphs[granularity].length > 0
                && (granularity == instance.granularity || granularity == instance.granularityToRemove) && instance.nextAnimate.coords != "") {
                //console.log(granularity + instance.nextAnimate.coords);
                instance.graphs[granularity].previous().animate(instance.nextAnimate.coords, instance.nextAnimate.delay, instance.nextAnimate.type);
                instance.graphs[granularity].current().animate(instance.nextAnimate.coords, instance.nextAnimate.delay, instance.nextAnimate.type);
                instance.graphs[granularity].next().animate(instance.nextAnimate.coords, instance.nextAnimate.delay, instance.nextAnimate.type);
            }
        });

        if (instance.granularityToRemove != "") {
            setTimeout(function () {
                instance.graphs[instance.granularityToRemove].previous().remove();
                instance.graphs[instance.granularityToRemove].current().remove();
                instance.graphs[instance.granularityToRemove].next().remove();
            }, instance.nextAnimate.delay);
        }
    };

    this.buildGraph = function (startInterval, endInterval) {
        var graph = new Graph(startInterval, endInterval, instance.blockWidth, instance.stroke, instance.displayHeight);
        return graph;
    };

    this.loadBlocksFor = function (graph) {
        instance.loadingCounter++;
        var source = instance.baseSource + graph.startInterval.startTime + "." + graph.endInterval.endTime + ";" + instance.granularity;

        return $.getJSON(source, function (data) {
            instance.buildBlocksFor(data, graph);
        });
    };

    this.buildBlocksFor = function (data, graph) {
        var blocks = new Array();
        var currentDataIndex = 0;
        var currentInterval = graph.startInterval;

        while (currentInterval.startTime <= graph.endInterval.startTime) {
            if (currentDataIndex < data.stats.length && currentInterval.containsTime(data.stats[currentDataIndex].time)) {
                buildFor(data.stats[currentDataIndex]);
                currentDataIndex++;
            }
            else {
                buildFor(null);
            }
            currentInterval = currentInterval.nextInterval();
        }

        graph.setBlocks(blocks);

        function buildFor(stat) {
            if (stat != null) {
                instance.orderFeelingsFor(stat);
                instance.normalizeData(stat);
                blocks.push(instance.computeBlockFor(stat, currentInterval));
            }
            else {
                var emptystat = {
                    time: currentInterval.startTime,
                    feelings: [
                        ["good", 0],
                        ["bad", 0],
                        ["neutral", 0]
                    ]
                };
                blocks.push(instance.computeBlockFor(emptystat, currentInterval));
            }
        }
    };

    this.orderFeelingsFor = function (stat) {
        var sortedFeelings = [];
        $.each(stat.feelings, function (index, element) {
            sortedFeelings.push([index, element]);
        });
        sortedFeelings.sort(function (a, b) {
            return b[1] - a[1];
        });
        stat.feelings = sortedFeelings;
    };

    this.normalizeData = function (stat) {
        var total = stat.feelings[0][1] + stat.feelings[1][1] + stat.feelings[2][1];
        for (var i = 0; i < stat.feelings.length; i++) {
            stat.feelings[i][1] = normalizeValue(stat.feelings[i][1]);
        }

        function normalizeValue(number) {
            return number * (instance.displayHeight - instance.spacer * 2 - instance.stroke * 2 - 30) / total;
        }
    };

    this.computeBlockFor = function (stat, interval) {
        var block = new Block(interval);
        setCoords();
        setSignLabel();
        setTimeLabel();
        return block;

        function setCoords() {
            var heightSum = instance.displayHeight - instance.stroke - 30;
            for (var i = stat.feelings.length - 1; i >= 0; i--) {
                block.coords[stat.feelings[i][0]].top = heightSum - stat.feelings[i][1];
                block.coords[stat.feelings[i][0]].bottom = heightSum;
                heightSum = heightSum - stat.feelings[i][1] - instance.spacer;
            }
        }

        function setSignLabel() {
            block.signLabel.text = (stat.feelings[0][1] != 0 ? instance.signs[stat.feelings[0][0]] : "");
            block.signLabel.y = parseFloat(block.coords[stat.feelings[0][0]].bottom / 2);
        }

        function setTimeLabel() {
            block.timeLabel = ( interval.containsDate(new Date()) ? "now" : block.interval);
        }
    };

    this.signs = {
        "neutral": "neutral",
        "good": "good",
        "bad": "bad"
    };

    this.types = ["good", "neutral", "bad"];

    this.graphs = {
        'hour': new OrderedLinkedList("startInterval"),
        'day': new OrderedLinkedList("startInterval"),
        'month': new OrderedLinkedList("startInterval"),
        'year': new OrderedLinkedList("startInterval")
    };

    this.granularity;
    this.baseSource;
    this.displayHeight;
    this.displayWidth;
    this.stroke;
    this.spacer;
    this.blockNumber;
    this.blockWidth;
    this.displayDiv;
    this.backDiv;
    this.forwardDiv;
    this.upDiv;
    this.downDiv;
    this.displaySvg;
    this.backSvg;
    this.forwardSvg;
    this.upSvg;
    this.downSvg;
    this.backButton;
    this.forwardButton;
    this.upButton;
    this.downButton;
    this.loadingCounter = 0;
    this.nextAnimate = {
        coords: "",
        delay: 1000,
        type: ">"
    };
    this.granularityToRemove = "";
}).call(timeline, jQuery);