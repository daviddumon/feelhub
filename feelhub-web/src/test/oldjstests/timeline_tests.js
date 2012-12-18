var TimeLineTests = new TestCase("TimeLineTests");

TimeLineTests.prototype = {

    setUp: function() {
        testDate = new Date();
        createDocumentForTimeLineTests();

        fakeData = {
            granularity : 'hour',
            stats :[
                {
                    time : testDate.minusHour(6).getTime(),
                    feelings : {
                        good : 18,
                        neutral : 268,
                        bad : 50
                    }
                },
                {
                    time : testDate.plusHour(4).getTime(),
                    feelings : {
                        good : 12,
                        bad : 53,
                        neutral : 228
                    }
                },
                {
                    time : testDate.plusHour(2).getTime(),
                    feelings : {
                        good : 23,
                        bad : 64,
                        neutral : 180
                    }
                }
            ]
        };

        $.getJSON = function(url, callback) {
            var jqxhr = {
                complete: function(){}
            };
            callback(fakeData);
            return jqxhr;
        };

        timeline.init('hour',baseSource, timelineWidth, timelineHeight, spacer, stroke, blockNumber, divName);
    },

    tearDown: function() {
        $.getJSON = originalGetJSON;
        resetTimeline();
    },

    testCanInitTimeline: function() {
        assertNotUndefined(timeline);
        assertNotNull(timeline);
        assertNotUndefined(timeline.displayWidth);
        assertEquals(timelineWidth, timeline.displayWidth);
        assertNotUndefined(timeline.displayHeight);
        assertEquals(timelineHeight, timeline.displayHeight);
        assertEquals(divName + "_display", timeline.displayDiv);
        assertEquals(divName + "_up", timeline.upDiv);
        assertEquals(divName + "_down", timeline.downDiv);
        assertEquals(divName + "_back", timeline.backDiv);
        assertEquals(divName + "_forward", timeline.forwardDiv);
        assertNotUndefined(timeline.blockNumber);
        assertEquals(5, timeline.blockNumber);
        assertNotUndefined(timeline.blockWidth);
        assertEquals(timelineWidth / 9, timeline.blockWidth);
        assertNotUndefined(timeline.baseSource);
        assertEquals(baseSource, timeline.baseSource);
        assertEquals(stroke, timeline.stroke);
        assertEquals(spacer, timeline.spacer);
        assertSame('hour', timeline.granularity);
    },

    testCanCreateDisplay: function() {
        assertNotUndefined(timeline.displaySvg);
        var svg = document.getElementById(timeline.displayDiv).getElementsByTagName('svg')[0];
        assertNotUndefined(svg);
        assertEquals(timelineWidth, svg.getAttribute('width'));
        assertEquals(timelineHeight, svg.getAttribute('height'));
    },

    testCanCreateButtons: function() {
        //assertNotUndefined(timeline.backSvg);
        //assertNotUndefined(timeline.forwardSvg);
        //assertNotUndefined(timeline.upSvg);
        //assertNotUndefined(timeline.downSvg);
        //assertNotUndefined(timeline.backButton);
        //assertNotUndefined(timeline.forwardButton);
        //assertNotUndefined(timeline.upButton);
        //assertNotUndefined(timeline.downButton);
        //var backSvg = document.getElementById(timeline.backDiv).getElementsByTagName('svg')[0];
        //var forwardSvg = document.getElementById(timeline.forwardDiv).getElementsByTagName('svg')[0];
        //var upSvg = document.getElementById(timeline.upDiv).getElementsByTagName('svg')[0];
        //var downSvg = document.getElementById(timeline.downDiv).getElementsByTagName('svg')[0];
        //assertNotUndefined(backSvg);
        //assertNotUndefined(forwardSvg);
        //assertNotUndefined(upSvg);
        //assertNotUndefined(downSvg);
        //assertEquals(60, backSvg.getAttribute('width'));
        //assertEquals(timelineHeight + 100, backSvg.getAttribute('height'));
        //assertEquals(60, forwardSvg.getAttribute('width'));
        //assertEquals(timelineHeight + 100, forwardSvg.getAttribute('height'));
        //assertEquals(timelineWidth, upSvg.getAttribute('width'));
        //assertEquals(50, upSvg.getAttribute('height'));
        //assertEquals(timelineWidth, downSvg.getAttribute('width'));
        //assertEquals(50, downSvg.getAttribute('height'));
        //var backButton = backSvg.getElementsByTagName("path");
        //assertNotUndefined(backButton);
        //assertEquals(1, backButton.length);
        //var forwardButton = forwardSvg.getElementsByTagName("path");
        //assertNotUndefined(forwardButton);
        //assertEquals(1, forwardButton.length);
        //var upButton = upSvg.getElementsByTagName("path");
        //assertNotUndefined(upButton);
        //assertEquals(1, upButton.length);
        //var downButton = downSvg.getElementsByTagName("path");
        //assertNotUndefined(downButton);
        //assertEquals(1, downButton.length);
    },

    testCanOrderFeelingsForStat: function() {
        var stat = fakeData.stats[0];

        timeline.orderFeelingsFor(stat);

        assertEquals(268, stat.feelings[0][1]);
        assertEquals(50, stat.feelings[1][1]);
        assertEquals(18, stat.feelings[2][1]);
    },

    testCanNormalizeData: function() {
        timeline.orderFeelingsFor(fakeData.stats[0]);
        var val1 = getNomarlizeFor(fakeData.stats[0].feelings[0][1]);
        var val2 = getNomarlizeFor(fakeData.stats[0].feelings[1][1]);
        var val3 = getNomarlizeFor(fakeData.stats[0].feelings[2][1]);

        timeline.normalizeData(fakeData.stats[0]);

        assertEquals(val1, fakeData.stats[0].feelings[0][1]);
        assertEquals(val2, fakeData.stats[0].feelings[1][1]);
        assertEquals(val3, fakeData.stats[0].feelings[2][1]);
    },

    testCanComputeBlockCoordsForStatAndInterval: function() {
        timeline.orderFeelingsFor(fakeData.stats[0]);
        var stat = fakeData.stats[0];

        var block = timeline.computeBlockFor(stat, testDate.interval("hour"));

        checkNeutralBlocksOK(stat, block);
        checkBadBlocksOK(stat, block);
        checkGoodBlocksOK(stat, block);
    },

    testInsertIntervalInBlock: function() {
        timeline.orderFeelingsFor(fakeData.stats[0]);
        var stat = fakeData.stats[0];
        var interval = testDate.minusHour(2).interval("hour");

        var block = timeline.computeBlockFor(stat, interval);

        assertSame(block.interval, interval);
    },

    testCanComputeSignLabelForBlock: function() {
        timeline.orderFeelingsFor(fakeData.stats[0]);
        timeline.normalizeData(fakeData.stats[0]);

        var block = timeline.computeBlockFor(fakeData.stats[0], testDate.interval("hour"));

        assertNotUndefined(block.signLabel);
        assertSame("neutral", block.signLabel.text);
    },

    testCanComputeTimeLabelForBlock: function() {
        timeline.orderFeelingsFor(fakeData.stats[0]);
        timeline.normalizeData(fakeData.stats[0]);

        var block = timeline.computeBlockFor(fakeData.stats[0], testDate.interval("hour"));

        assertNotUndefined(block.timeLabel);
        assertSame("now", block.timeLabel);
    },

    testCanLoadData: function() {
        var graph = fakeGraph();

        timeline.loadBlocksFor(graph);

        assertEquals(1, graph.blocks.length);
    },

    testCanBuildAGraph: function() {
        var graph = timeline.buildGraph();

        assertNotUndefined(graph);
    },

    testLoadDataIncreaseLoadingCounter: function() {
        var graph = fakeGraph();

        timeline.loadBlocksFor(graph);

        assertEquals(1, timeline.loadingCounter);
    },

    testCanDecreaseCounterWhenCheckAnimate: function() {
        timeline.loadingCounter = 2;

        timeline.checkAnimate();

        assertEquals(1, timeline.loadingCounter);
    },

    testCallAnimateIfLoadingCounterReachZero: function() {
        var originalAnimate = timeline.animate;
        var call = false;
        timeline.loadingCounter = 1;
        timeline.animate = function() { call = true;};

        timeline.checkAnimate();

        assertTrue(call);
        timeline.animate = originalAnimate;
    },

    testHasGranularityOrderedLinkedListOfGraphs: function() {
        assertNotUndefined(timeline.graphs);
        assertEquals(0, timeline.graphs["hour"].length);
        assertEquals(0, timeline.graphs["day"].length);
        assertEquals(0, timeline.graphs["month"].length);
        assertEquals(0, timeline.graphs["year"].length);
    },

    testCanLoadMultiplesBlocks: function() {
        var graph = fakeGraph();
        testDate.minusHour(9);
        graph.startInterval = testDate.interval("hour");

        timeline.buildBlocksFor(fakeData, graph);

        assertEquals(10, graph.blocks.length);
        var interval = graph.startInterval;
        for(var i = 0; i < 10; i++) {
            assertSame(interval.startTime, graph.blocks[i].interval.startTime);
            interval = interval.nextInterval();
        }
    },

    testCanLoadInitialGraphs: function() {
        timeline.loadInitialGraphs(0,0);

        //assertEquals(3, timeline.graphs[timeline.granularity].length);
    },

    testCanAnimateBack: function() {
        //timeline.loadInitialGraphs(0,0);
        //
        //timeline.back();
        //
        //assertEquals(4, timeline.graphs[timeline.granularity].length);
        //assertTrue(new Date().minusHour(12).interval("hour").equals(timeline.graphs[timeline.granularity].previous().startInterval));
        //assertTrue(new Date().minusHour(7).interval("hour").equals(timeline.graphs[timeline.granularity].current().startInterval));
        //assertTrue(new Date().minusHour(2).interval("hour").equals(timeline.graphs[timeline.granularity].next().startInterval));
    },

    testCanAnimateForward: function() {
    //    timeline.loadInitialGraphs(0,0);
    //
    //    timeline.forward();
    //
    //    assertEquals(4, timeline.graphs[timeline.granularity].length);
    //    assertTrue(new Date().minusHour(2).interval("hour").equals(timeline.graphs[timeline.granularity].previous().startInterval));
    //    assertTrue(new Date().plusHour(3).interval("hour").equals(timeline.graphs[timeline.granularity].current().startInterval));
    //    assertTrue(new Date().plusHour(8).interval("hour").equals(timeline.graphs[timeline.granularity].next().startInterval));
    },

    testCanAnimateUp: function() {
        //timeline.loadInitialGraphs(0,0);
        //
        //timeline.up();
        //
        //assertSame("month", timeline.granularity);
        //assertEquals(3, timeline.graphs[timeline.granularity].length);
    },

    testHasGranularityToRemove: function() {
        //timeline.loadInitialGraphs(0,0);
        //
        //timeline.up();
        //
        //assertSame("hour", timeline.granularityToRemove);
    },

    testCanAnimateDown: function() {
        timeline.granularity = "day";
        timeline.loadInitialGraphs(0,0);

        timeline.down();

        assertSame("hour", timeline.granularity);
        assertEquals(3, timeline.graphs[timeline.granularity].length);
    }
};

function createDocumentForTimeLineTests() {
    var displayDiv  = document.createElement('displayDiv');
    var backDiv     = document.createElement('backDiv');
    var forwardDiv  = document.createElement('forwardDiv');
    var upDiv  = document.createElement('upDiv');
    var downDiv  = document.createElement('downDiv');

    displayDiv.setAttribute("id", divName + "_display");
    backDiv.setAttribute("id", divName + "_back");
    forwardDiv.setAttribute("id", divName + "_forward");
    upDiv.setAttribute("id", divName + "_up");
    downDiv.setAttribute("id", divName + "_down");

    document.body.appendChild(displayDiv);
    document.body.appendChild(backDiv);
    document.body.appendChild(forwardDiv);
    document.body.appendChild(upDiv);
    document.body.appendChild(downDiv);
};

function fakeGraph() {
    return timeline.buildGraph(testDate.interval("hour"), testDate.interval("hour"));
};

function resetTimeline() {
    timeline.loadingCounter = 0;

    timeline.graphs = {
        'hour': new OrderedLinkedList("startInterval"),
        'day': new OrderedLinkedList("startInterval"),
        'month': new OrderedLinkedList("startInterval"),
        'year': new OrderedLinkedList("startInterval")
    };
};

function getNomarlizeFor(val) {
    var total = fakeData.stats[0].feelings[0][1] + fakeData.stats[0].feelings[1][1] + fakeData.stats[0].feelings[2][1];
    return val * (timeline.displayHeight - timeline.spacer * 2 - timeline.stroke * 2 - 30) / total;
};

function checkNeutralBlocksOK(stat, block) {
    assertNotUndefined(block.coords["neutral"]);
    assertNotUndefined(block.coords["neutral"].top);
    assertEquals(timeline.displayHeight - 30 - timeline.stroke - timeline.spacer * 2 - stat.feelings[2][1] - stat.feelings[1][1] - stat.feelings[0][1], block.coords["neutral"].top);
    assertNotUndefined(block.coords["neutral"].bottom);
    assertEquals(timeline.displayHeight - 30 - timeline.stroke - timeline.spacer * 2 - stat.feelings[2][1] - stat.feelings[1][1], block.coords["neutral"].bottom);
};

function checkBadBlocksOK(stat, block) {
    assertNotUndefined(block.coords["bad"]);
    assertNotUndefined(block.coords["bad"].top);
    assertEquals(timeline.displayHeight - 30 - timeline.stroke - timeline.spacer - stat.feelings[2][1] - stat.feelings[1][1], block.coords["bad"].top);
    assertNotUndefined(block.coords["bad"].bottom);
    assertEquals(timeline.displayHeight - 30 - timeline.stroke - timeline.spacer - stat.feelings[2][1], block.coords["bad"].bottom);
};

function checkGoodBlocksOK(stat, block) {
    assertNotUndefined(block.coords["good"]);
    assertNotUndefined(block.coords["good"].top);
    assertEquals(timeline.displayHeight - 30 - timeline.stroke - stat.feelings[2][1], block.coords["good"].top);
    assertNotUndefined(block.coords["good"].bottom);
    assertEquals(timeline.displayHeight - 30 - timeline.stroke, block.coords["good"].bottom);
};

var fakeData;
var divName = "timeline";
var timelineWidth = 600;
var timelineHeight = 300;
var spacer = 5;
var stroke = 3;
var blockNumber = 5;
var baseSource = "http://localhost:8080/feelhub/dummy/";
var originalGetJSON = $.getJSON;
var testDate;