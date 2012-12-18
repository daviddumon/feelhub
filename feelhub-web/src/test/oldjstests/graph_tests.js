var GraphTests = new TestCase("GraphTests");

GraphTests.prototype = {

    setUp: function() {
        createDocument();
    },

    testCanCreateAGraph: function() {
        var graph = new Graph(start, end);

        assertNotUndefined(graph);
    },

    testCanCreateABlock: function() {
        var interval = new Date().interval("hour");

        var block = new Block(interval);

        assertSame(interval, block.interval);
    },

    testCanSetStartAndEnd: function() {
        var graph = new Graph(start, end);

        assertEquals(start, graph.startInterval);
        assertEquals(end, graph.endInterval);
    },

    testCanSetBlockWidth: function() {
        var graph = new Graph(start, end, 10);

        assertEquals(10, graph.blockWidth);
    },

    testCanSetBlockStroke: function() {
        var graph = new Graph(start, end, 10, 5);

        assertEquals(5, graph.stroke);
    },

    testCanSetGraphHeight: function() {
        var graph = new Graph(start, end, 10, 5, 200);

        assertEquals(200, graph.height);
    },

    testCanSetData: function() {
        var graph = new Graph(start, end, 10, 5, 200);

        graph.setBlocks(blocks);

        assertNotUndefined(graph.blocks);
        assertNotNull(graph.blocks);
        assertEquals(3, graph.blocks.length);
        assertSame(blocks[0].interval, graph.blocks[0].interval);
        assertSame(blocks[1].interval, graph.blocks[1].interval);
        assertSame(blocks[2].interval, graph.blocks[2].interval);
    },

    testCanBuildPaths: function() {
        var graph = new Graph(start, end, 10, 5, 200);
        graph.setBlocks(blocks);

        assertSame(buildFakePath("good", 10), graph.path("good", 0, 0));
        assertSame(buildFakePath("bad", 10), graph.path("bad", 0, 0));
        assertSame(buildFakePath("neutral", 10), graph.path("neutral", 0, 0));
    },

    testCanBuildTopStrokePaths: function() {
        var graph = new Graph(start, end, 10, 5, 200);
        graph.setBlocks(blocks);

        assertSame(buildFakeTopStroke("good", 10), graph.top("good", 0, 0));
        assertSame(buildFakeTopStroke("bad", 10), graph.top("bad", 0, 0));
        assertSame(buildFakeTopStroke("neutral", 10), graph.top("neutral", 0, 0));
    },

    testCanBuildBottomStrokePaths: function() {
        var graph = new Graph(start, end, 10, 5, 200);
        graph.setBlocks(blocks);

        assertSame(buildFakeBottomStroke("good", 10), graph.bottom("good", 0, 0));
        assertSame(buildFakeBottomStroke("bad", 10), graph.bottom("bad", 0, 0));
        assertSame(buildFakeBottomStroke("neutral", 10), graph.bottom("neutral", 0, 0));
    },

    testCanDrawAGraph: function() {
        var svg = new Raphael("svg", 800, 800);
        var graph = new Graph(start, end, 10, 5, 200);
        graph.setBlocks(blocks);

        graph.draw(svg, 100, 100, feelhub.colors);

        var paths = document.getElementById("svg").getElementsByTagName('path');
        assertEquals(9, paths.length);
        assertEquals(graph.stroke, parseInt(paths[0].getAttribute("stroke-width")));
        assertEquals(graph.stroke, parseInt(paths[1].getAttribute("stroke-width")));
        assertEquals(graph.stroke, parseInt(paths[3].getAttribute("stroke-width")));
        assertEquals(graph.stroke, parseInt(paths[4].getAttribute("stroke-width")));
        assertEquals(graph.stroke, parseInt(paths[6].getAttribute("stroke-width")));
        assertEquals(graph.stroke, parseInt(paths[7].getAttribute("stroke-width")));

        var labels = document.getElementById("svg").getElementsByTagName('text');
        assertEquals(6, labels.length);
        assertSame(blocks[0].signLabel.text, labels[0].getElementsByTagName('tspan')[0].textContent);
        assertSame(blocks[1].signLabel.text, labels[2].getElementsByTagName('tspan')[0].textContent);
        assertSame(blocks[2].signLabel.text, labels[4].getElementsByTagName('tspan')[0].textContent);
        assertSame(blocks[0].timeLabel, labels[1].getElementsByTagName('tspan')[0].textContent);
        assertSame(blocks[1].timeLabel, labels[3].getElementsByTagName('tspan')[0].textContent);
        assertSame(blocks[2].timeLabel, labels[5].getElementsByTagName('tspan')[0].textContent);
    },

    testCanDrawLabelsAtGoodXCoords: function() {
        var svg = new Raphael("svg",800,800);
        var graph = new Graph(start, end, 10, 5, 200);
        graph.setBlocks(blocks);

        graph.draw(svg, 100, 100, feelhub.colors);

        var labels = document.getElementById("svg").getElementsByTagName('text');
        assertEquals(105, parseInt(labels[0].getAttribute("x")));
        //assertEquals(133, parseInt(labels[0].getAttribute("y")));
    },

    testDoNotDrawSignLabelForEmptyStat: function() {
        var svg = new Raphael("svg",800,800);
        var graph = new Graph(start, end, 10, 5, 200);
        graph.setBlocks(blocksWithEmptySignLabels);

        graph.draw(svg, 100, 100, feelhub.colors);

        var labels = document.getElementById("svg").getElementsByTagName('text');
        assertEquals(4, labels.length);
    },

    testAllGraphObjectsInASet: function() {
        var svg = new Raphael("svg",800,800);
        var graph = new Graph(start, end, 10, 5, 200);
        graph.setBlocks(blocks);

        graph.draw(svg, 100, 100, feelhub.colors);

        assertNotUndefined(graph.set);
        assertEquals(15, graph.set.length);
    },

    testCanRemoveGraphFromDOM: function() {
        var svg = new Raphael("svg",800,800);
        var graph = new Graph(start, end, 10, 5, 200);
        graph.setBlocks(blocks);
        graph.draw(svg, 100, 100, feelhub.colors);

        graph.remove();

        var paths = document.getElementById("svg").getElementsByTagName('path');
        var labels = document.getElementById("svg").getElementsByTagName('text');
        assertEquals(0, paths.length);
        assertEquals(0, labels.length);
    },

    testCanCompareGraphs: function() {
        var firstGraph = new Graph(start, end, 10, 5, 200);
        firstGraph.setBlocks(blocks);
        var secondGraph = new Graph(start.previousInterval(), end.previousInterval(), 10, 5, 200);
        secondGraph.setBlocks(blocksWithEmptySignLabels);
        var thirdGraph = new Graph(start, end, 10, 5, 200);
        thirdGraph.setBlocks(blocks);

        assertTrue(firstGraph.equals(thirdGraph));
        assertFalse(firstGraph.equals(secondGraph));
    },

    testDrawHasACallbackFunction: function() {
        var call = false;
        var svg = new Raphael("svg",800,800);
        var graph = new Graph(start, end, 10, 5, 200);
        graph.setBlocks(blocks);
        graph.draw(svg, 100, 100, feelhub.colors, function(){
            call = true;
        });

        assertTrue(call);
    }
};

function createDocument() {
    var svg  = document.createElement('svg');
    svg.setAttribute("id", "svg");
    document.body.appendChild(svg);
};

var start = new Date().minusDay(1).interval("hour");
var end = new Date().plusDay(1).interval("hour");

var blocks = [
    {
        interval: new Date().interval("hour"),
        timeLabel: "now",
        signLabel: {text: "+", y: 10},

        coords: {
            good: {
                top: 27,
                bottom: 18
                },
            bad: {
                top: 12,
                bottom: 31
                },
            neutral: {
                top: 12,
                bottom: 31
                }
        }
    },
    {
        interval: new Date().plusHour(1).interval("hour"),
        timeLabel: "demain",
        signLabel: {text: "-", y: 10},

        coords: {
            good: {
                top: 3,
                bottom: 12
                },
            bad: {
                top: 34,
                bottom: 31
                },
            neutral: {
                top: 234,
                bottom: 11
                }
        }
    },
    {
        interval: new Date().plusHour(2).interval("hour"),
        timeLabel: "23 sep",
        signLabel: {text: "=", y: 10},

        coords: {
            good: {
                top: 32,
                bottom: 12
                },
            bad: {
                top: 389,
                bottom: 18
                },
            neutral: {
                top: 90,
                bottom: 31
                }
        }
    }
];

var blocksWithEmptySignLabels = [
    {
        interval: new Date().interval("hour"),
        timeLabel: "now",
        signLabel: {text: "", y: 10},

        coords: {
            good: {
                top: 27,
                bottom: 18
                },
            bad: {
                top: 12,
                bottom: 31
                },
            neutral: {
                top: 12,
                bottom: 31
                }
        }
    },
    {
        interval: new Date().plusHour(1).interval("hour"),
        timeLabel: "demain",
        signLabel: {text: "", y: 10},

        coords: {
            good: {
                top: 3,
                bottom: 12
                },
            bad: {
                top: 34,
                bottom: 31
                },
            neutral: {
                top: 234,
                bottom: 11
                }
        }
    },
    {
        interval: new Date().plusHour(2).interval("hour"),
        timeLabel: "23 sep",
        signLabel: {text: "=", y: 10},

        coords: {
            good: {
                top: 32,
                bottom: 12
                },
            bad: {
                top: 389,
                bottom: 18
                },
            neutral: {
                top: 90,
                bottom: 31
                }
        }
    }
];

function buildFakePath(type, blockWidth) {
    var path = "M" + blockWidth + "," + blocks[0].coords[type].bottom
    + "L0," + blocks[0].coords[type].bottom
    + "L0," + blocks[0].coords[type].top
    + "L" + blockWidth + "," + blocks[0].coords[type].top;

    var bezierPointX01 = (blockWidth * 3) / 2;
    var bezierPointX12 = (blockWidth * 7) / 2;

    path += "C" + bezierPointX01 + "," + blocks[0].coords[type].top
    + "," + bezierPointX01 + "," + blocks[1].coords[type].top
    + "," + blockWidth * 2 + "," + blocks[1].coords[type].top
    + "L" + blockWidth * 3 + "," + blocks[1].coords[type].top
    + "C" + bezierPointX12 + "," + blocks[1].coords[type].top
    + "," + bezierPointX12 + "," + blocks[2].coords[type].top
    + "," + blockWidth * 4 + "," + blocks[2].coords[type].top
    + "L" + blockWidth * 5 + "," + blocks[2].coords[type].top;

    path += "L" + blockWidth * 5 + "," + blocks[blocks.length - 1].coords[type].bottom;

    path += "L" + blockWidth * 4 + "," + blocks[2].coords[type].bottom
    + "C" + bezierPointX12 + "," + blocks[2].coords[type].bottom
    + "," + bezierPointX12 + "," + blocks[1].coords[type].bottom
    + "," + blockWidth * 3 + "," + blocks[1].coords[type].bottom
    + "L" + blockWidth * 2 + "," + blocks[1].coords[type].bottom
    + "C" + bezierPointX01 + "," + blocks[1].coords[type].bottom
    + "," + bezierPointX01 + "," + blocks[0].coords[type].bottom
    + "," + blockWidth * 1 + "," + blocks[0].coords[type].bottom;

    return path;
};

function buildFakeTopStroke(type, blockWidth) {
    var path = "M0," + blocks[0].coords[type].top
    + "L" + blockWidth + "," + blocks[0].coords[type].top;

    var bezierPointX01 = blockWidth * 3 / 2;
    var bezierPointX12 = blockWidth * 7 / 2;

    path += "C" + bezierPointX01 + "," + blocks[0].coords[type].top
    + "," + bezierPointX01 + "," + blocks[1].coords[type].top
    + "," + blockWidth * 2 + "," + blocks[1].coords[type].top
    + "L" + blockWidth * 3 + "," + blocks[1].coords[type].top
    + "C" + bezierPointX12 + "," + blocks[1].coords[type].top
    + "," + bezierPointX12 + "," + blocks[2].coords[type].top
    + "," + blockWidth * 4 + "," + blocks[2].coords[type].top
    + "L" + blockWidth * 5 + "," + blocks[2].coords[type].top;

    return path;
};

function buildFakeBottomStroke(type, blockWidth) {
    var path = "M0," + blocks[0].coords[type].bottom
    + "L" + blockWidth + "," + blocks[0].coords[type].bottom

    var bezierPointX01 = blockWidth * 3 / 2;
    var bezierPointX12 = blockWidth * 7 / 2;

    path += "C" + bezierPointX01 + "," + blocks[0].coords[type].bottom
    + "," + bezierPointX01 + "," + blocks[1].coords[type].bottom
    + "," + blockWidth * 2 + "," + blocks[1].coords[type].bottom
    + "L" + blockWidth * 3 + "," + blocks[1].coords[type].bottom
    + "C" + bezierPointX12 + "," + blocks[1].coords[type].bottom
    + "," + bezierPointX12 + "," + blocks[2].coords[type].bottom
    + "," + blockWidth * 4 + "," + blocks[2].coords[type].bottom
    + "L" + blockWidth * 5 + "," + blocks[2].coords[type].bottom;

    return path;
};