var FlowPositionTests = new TestCase("FlowPositionTests");

FlowPositionTests.prototype = {

    setUp:function () {
        createDocumentForFlowPositionTests();
        flow = new Flow();
        fakeMaxBox(flow);
    },

    testCanCreateALine:function () {
        flow.createLine();

        assertNotUndefined(flow.lines);
        assertEquals(1, flow.lines.length);
        assertEquals(flow.maxBox, flow.lines[0].length);
        for (var i = 0; i < flow.maxBox; i++) {
            assertEquals(0, flow.lines[0][i]);
        }
    },

    testHasFreeLinesFlagArray: function() {
        flow.createLine();
        flow.createLine();
        flow.createLine();

        assertNotUndefined(flow.freeLines);
        assertEquals(3,flow.freeLines.length);
        assertEquals(1, flow.freeLines[0]);
        assertEquals(1, flow.freeLines[1]);
        assertEquals(1, flow.freeLines[2]);
    },

    testCanPutBox: function() {
        flow.createLine();

        flow.putBox(0,0,1);

        assertEquals(1, flow.lines[0][0]);
        assertEquals(0, flow.lines[0][1]);
        assertEquals(0, flow.lines[0][2]);
        assertEquals(0, flow.lines[0][3]);
    },

    testPutBoxCreateLine: function() {
        flow.putBox(0,0,1);

        assertEquals(1, flow.lines[0][0]);
        assertEquals(0, flow.lines[0][1]);
        assertEquals(0, flow.lines[0][2]);
        assertEquals(0, flow.lines[0][3]);
    },

    testCanPut2BoxesInLine: function() {
        flow.createLine();

        flow.putBox(0,1,1);
        flow.putBox(0,2,1);

        assertEquals(0, flow.lines[0][0]);
        assertEquals(1, flow.lines[0][1]);
        assertEquals(1, flow.lines[0][2]);
        assertEquals(0, flow.lines[0][3]);
    },

    testCanPutBoxWithSizeOf2: function() {
        flow.createLine();

        flow.putBox(0,1,2);

        assertEquals(2, flow.lines.length);
        assertEquals(0, flow.lines[0][0]);
        assertEquals(1, flow.lines[0][1]);
        assertEquals(1, flow.lines[0][2]);
        assertEquals(0, flow.lines[0][3]);
        assertEquals(0, flow.lines[1][0]);
        assertEquals(1, flow.lines[1][1]);
        assertEquals(1, flow.lines[1][2]);
        assertEquals(0, flow.lines[1][3]);
    },

    testCanSetFullLineFlag: function() {
        flow.createLine();
        flow.createLine();
        flow.lines[0][0] = 1;
        flow.lines[0][1] = 1;
        flow.lines[0][2] = 0;
        flow.lines[0][3] = 0;
        flow.lines[1][0] = 1;
        flow.lines[1][1] = 0;
        flow.lines[1][2] = 0;
        flow.lines[1][3] = 0;

        flow.putBox(0,2,2);

        assertEquals(0, flow.freeLines[0]);
        assertEquals(1, flow.freeLines[1]);
    },

    testCanTestForFreeBlock: function() {
        flow.createLine();
        flow.lines[0][0] = 1;
        flow.lines[0][1] = 1;
        flow.lines[0][2] = 1;
        flow.lines[0][3] = 0;

        var notFree = flow.isBlockFree(0,1,1);
        var free = flow.isBlockFree(0,3,1);

        assertFalse(notFree);
        assertTrue(free);
    },

    testCanTestForFreeBlockOfBigSize: function() {
        flow.createLine();
        flow.lines[0][0] = 1;
        flow.lines[0][1] = 0;
        flow.lines[0][2] = 0;
        flow.lines[0][3] = 1;

        var free = flow.isBlockFree(0,1,2);
        var notFree = flow.isBlockFree(0,2,2);

        assertFalse(notFree);
        assertTrue(free);
    },

    testCanTestForFreeBlockWithBadSize: function() {
        flow.createLine();
        flow.lines[0][0] = 0;
        flow.lines[0][1] = 0;
        flow.lines[0][2] = 0;
        flow.lines[0][3] = 0;

        var notFree = flow.isBlockFree(0,0,5);
        var free = flow.isBlockFree(0,0,4);

        assertFalse(notFree);
        assertTrue(free);
    },

    testCanTestForSquare: function() {
        flow.createLine();
        flow.createLine();
        flow.lines[0][0] = 1;
        flow.lines[0][1] = 0;
        flow.lines[0][2] = 0;
        flow.lines[0][3] = 0;
        flow.lines[1][0] = 0;
        flow.lines[1][1] = 1;
        flow.lines[1][2] = 0;
        flow.lines[1][3] = 0;

        var good = flow.testForSquare(0,2,2);
        var bad = flow.testForSquare(0,1,2);

        assertTrue(good);
        assertFalse(bad);
    },

    testCanTestForSquareWithLessLine: function() {
        flow.createLine();
        flow.lines[0][0] = 1;
        flow.lines[0][1] = 0;
        flow.lines[0][2] = 0;
        flow.lines[0][3] = 0;

        var good = flow.testForSquare(0,1,3);
        var bad = flow.testForSquare(0,0,4);

        assertTrue(good);
        assertFalse(bad);
    },

    testCanTestForSquareWithFullLine: function() {
        flow.createLine();
        flow.createLine();
        flow.createLine();
        flow.lines[0][0] = 1;
        flow.lines[0][1] = 1;
        flow.lines[0][2] = 1;
        flow.lines[0][3] = 1;
        flow.lines[1][0] = 1;
        flow.lines[1][1] = 1;
        flow.lines[1][2] = 1;
        flow.lines[1][3] = 1;
        flow.lines[2][0] = 1;
        flow.lines[2][1] = 1;
        flow.lines[2][2] = 1;
        flow.lines[2][3] = 1;

        var bad = flow.testForSquare(0,0,3);

        assertFalse(bad);
    },

    testCanFindNextFreeSpace: function() {
        flow.createLine();
        flow.lines[0][0] = 1;
        flow.lines[0][1] = 1;
        flow.lines[0][2] = 1;
        flow.lines[0][3] = 0;

        var position = flow.findNextFreeSpace(1);

        assertEquals(0, position.line);
        assertEquals(3, position.index);
    },

    testCanFindNextFreeSpaceInner: function() {
        flow.createLine();
        flow.createLine();
        flow.lines[0][0] = 1;
        flow.lines[0][1] = 0;
        flow.lines[0][2] = 1;
        flow.lines[0][3] = 1;
        flow.lines[1][0] = 1;
        flow.lines[1][1] = 1;
        flow.lines[1][2] = 1;
        flow.lines[1][3] = 1;

        var position = flow.findNextFreeSpace(1);

        assertEquals(0, position.line);
        assertEquals(1, position.index);
    },

    testCanFindNextFreeSpaceIfNoLines: function() {
        var position = flow.findNextFreeSpace(1);

        assertEquals(0, position.line);
        assertEquals(0, position.index);
    },

    testCanFindNextFreeSpaceFullLine: function() {
        flow.createLine();
        flow.lines[0][0] = 1;
        flow.lines[0][1] = 1;
        flow.lines[0][2] = 1;
        flow.lines[0][3] = 1;

        var position = flow.findNextFreeSpace(2);

        assertEquals(1, position.line);
        assertEquals(0, position.index);
    },

    testCanFindNextFreeSpaceOfSize2: function() {
        flow.createLine();
        flow.createLine();
        flow.lines[0][0] = 1;
        flow.lines[0][1] = 0;
        flow.lines[0][2] = 0;
        flow.lines[0][3] = 0;
        flow.lines[1][0] = 1;
        flow.lines[1][1] = 1;
        flow.lines[1][2] = 0;
        flow.lines[1][3] = 0;

        var position = flow.findNextFreeSpace(2);

        assertEquals(0, position.line);
        assertEquals(2, position.index);
    },

    testCanFindNextFreeSpaceWithSizeOf2And1Line: function() {
        flow.createLine();
        flow.lines[0][0] = 1;
        flow.lines[0][1] = 0;
        flow.lines[0][2] = 0;
        flow.lines[0][3] = 1;

        var position = flow.findNextFreeSpace(2);

        assertEquals(0, position.line);
        assertEquals(1, position.index);
    },

    testCanFindNextFreeSpaceWithSizeOfMax: function() {
        flow.createLine();
        flow.lines[0][0] = 0;
        flow.lines[0][1] = 0;
        flow.lines[0][2] = 0;
        flow.lines[0][3] = 0;

        var position = flow.findNextFreeSpace(flow.maxBox);

        assertEquals(0, position.line);
        assertEquals(0, position.index);
    },

    testCanGetTopPosition: function() {
        flow.createLine();

        var top = flow.getTopPosition(0);

        assertEquals(0, top);
    },

    testCanGetTopPositionForIndexOne: function() {
        flow.createLine();

        var top = flow.getTopPosition(1);

        assertEquals(flow.initial + flow.numericalValueFrom(flow.margin), top);
    },


};

function createDocumentForFlowPositionTests() {
    var webpageUL = document.createElement('webpageUL');
    webpageUL.setAttribute("id", "feelings");
    webpageUL.style.width = "800px";
    document.body.appendChild(webpageUL);
}

function fakeMaxBox(flow) {
    flow.maxBox = 4;
}