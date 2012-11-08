var FlowTests = new TestCase("FlowTests");

FlowTests.prototype = {

    setUp: function() {
        createDocumentForFlowTests();
        flow = new Flow("flow.css","webpage","li",".feeling");
    },

    testCanCreateAFlow: function() {
        assertNotUndefined(flow);
        assertNotUndefined(flow.cssIndex);
        assertSame(0, flow.cssIndex);
        assertNotUndefined(flow.webpage);
    },

    testCanFindStyleSheetIndex: function() {
        var styleSheet = flow.findStyleSheetIndex();

        assertEquals(0, styleSheet);
    },

    testCanFindPropertyValueFromCSS: function() {
        var padding = flow.extractValueFromCSS("padding");

        assertSame("10px", padding);
    },

    testCanInitValues: function() {
        assertNotUndefined(flow.width);
        assertNotUndefined(flow.padding);
        assertNotUndefined(flow.margin);
        assertSame("20%", flow.width);
        assertSame("10px", flow.padding);
        assertSame("20px", flow.margin);
    },

    testCanDrawABox: function() {
        flow.drawBox("myfeeling","feeling feeling_good");

        var box = document.getElementById("webpage").getElementsByTagName('li');
        assertEquals(1, box.length);
        assertEquals("myfeeling", box[0].innerHTML);
        assertEquals("feeling feeling_good", box[0].className);
    },

    testCanDrawABoxLink: function() {
        flow.drawBoxLink("myfeeling","feeling feeling_good","fakelink");

        var feelinglink = document.getElementById("webpage").getElementsByTagName('a');
        assertEquals(1, feelinglink.length);
        assertEquals("feelinglink", feelinglink[0].className);
        assertEquals(1, feelinglink[0].childElementCount);
        assertEquals("myfeeling", feelinglink[0].firstChild.innerHTML);
        assertEquals("feeling feeling_good", feelinglink[0].firstChild.className);
    },

    testCanSetInitialMaxWidthWithCSS: function() {
        flow.drawBox("myfeeling", "feeling");

        var box = document.getElementById("webpage").getElementsByTagName('li');
        assertEquals(0, getWidth(box[0]));
    },

    testReturnIDFromDrawing: function() {
        var id = flow.drawBox("myfeeling", "feeling");

        assertEquals("feeling_1", id);
    },

    testCanSetID: function(){
        var id = flow.drawBox("myfeeling", "feeling");

        assertEquals(id, document.getElementsByTagName("li")[0].getAttribute("id"));
    },

    testHasAMinHeightEqualsToInitialWidth: function() {
        var id = flow.drawBox("myfeeling", "feeling");

        flow.compute(id);

        var box = document.getElementById("webpage").getElementsByTagName('li');
        var width = getWidth(box[0]);
        assertEquals(width, getHeight(box[0]));
    },

    testDoNotMinimizeHeightIfSuperiorToWidth: function() {
        var id = flow.drawBox(infinystring, "feeling");
        var box = document.getElementById("webpage").getElementsByTagName('li');
        var initialWidth = getWidth(box[0]);

        flow.compute(id);

        assertNotEquals(initialWidth, getHeight(box[0]));
    },

    testCanGetNumericalValueFromPercent: function() {
        var numerical = flow.numericalValueFrom("1%");

        assertEquals(0.01, numerical);
    },

    testCanGetNumericalValueFromPx: function() {
        var numerical = flow.numericalValueFrom("10px");

        assertEquals(10, numerical);
    },

    testCanFindMaxBox: function() {
        assertEquals(3, flow.maxBox);
    },

    testCanFindNextWidthFromInitial: function() {
        var docWidth = parseInt(document.width || document.body.offsetWidth);
        var margin = flow.numericalValueFrom(flow.margin);
        var padding = flow.numericalValueFrom(flow.padding);

        var next = flow.findWidthForSize(100);
        
        assertEquals(100 + margin + flow.initial - 2 * padding, next);
    },

    testCanGrowOnlyToMaxBox: function() {
        var _findNextWidth = flow.findWidthForSize;
        var count = 0;
        flow.findWidthForSize = function() {count++};
        var id = flow.drawBox(infinystring, "feeling");

        flow.compute(id);

        assertEquals(flow.maxBox - 1, count);
        flow.findWidthForSize = _findNextWidth;
    },

    testEnsurePositionAbsolute: function() {
        var id = flow.drawBox("myfeeling", "feeling");

        var box = document.getElementsByTagName("li");
        assertSame("absolute", window.getComputedStyle(box[0], null).getPropertyCSSValue('position').cssText);
    },
};

function createDocumentForFlowTests() {
    var webpageUL = document.createElement('webpageUL');
    webpageUL.setAttribute("id","webpage");
    webpageUL.style.width = "400px";
    document.body.appendChild(webpageUL);
}

function getWidth(item) {
    var width = window.getComputedStyle(item, null).getPropertyCSSValue('width').cssText;
    return parseInt(width.substring(0,width.length - 2));
}

function getHeight(item) {
    var height = window.getComputedStyle(item, null).getPropertyCSSValue('height').cssText;
    return parseInt(height.substring(0, height.length -2));
}

var verylongstring = "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! ";
var infinystring = "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
+ "This is a test string ! This is a test string ! This is a test string ! This is a test string ! This is a test string ! "
;