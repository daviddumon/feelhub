var FeedTests = new TestCase("FeedTests");

FeedTests.prototype = {

    setUp: function () {
        createDocumentForTest();
        runTimeLine();
    },

    //testIsBoundBackButton: function() {
    //    var isButtonClicked = false;
    //    timeline.back = function() {isButtonClicked = true;};
    //    var button = document.getElementById(timeline.backDiv).getElementsByTagName("path")[0];
    //
    //    createClickEventFor(button);
    //
    //    assertTrue(isButtonClicked);
    //},
    //
    //testIsBoundForwardButton: function() {
    //    var isButtonClicked = false;
    //    timeline.forward = function() {isButtonClicked = true;};
    //    var button = document.getElementById(timeline.forwardDiv).getElementsByTagName("path")[0];
    //
    //    createClickEventFor(button);
    //
    //    assertTrue(isButtonClicked);
    //},
    //
    //testIsBoundUpButton: function() {
    //    var isButtonClicked = false;
    //    timeline.up = function() {isButtonClicked = true;};
    //    var button = document.getElementById(timeline.upDiv).getElementsByTagName("path")[0];
    //
    //    createClickEventFor(button);
    //
    //    assertTrue(isButtonClicked);
    //},
    //
    //testIsBoundDownButton: function() {
    //    var isButtonClicked = false;
    //    timeline.down = function() {isButtonClicked = true;};
    //    var button = document.getElementById(timeline.downDiv).getElementsByTagName("path")[0];
    //
    //    createClickEventFor(button);
    //
    //    assertTrue(isButtonClicked);
    //}
};

var divName = "timeline";

function createDocumentForTest() {
    var displayDiv = document.createElement('displayDiv');
    var backDiv = document.createElement('backDiv');
    var forwardDiv = document.createElement('forwardDiv');
    var upDiv = document.createElement('upDiv');
    var downDiv = document.createElement('downDiv');

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

function createClickEventFor(element) {
    var click = element.ownerDocument.createEvent('MouseEvents');
    click.initMouseEvent('click', true, true, element.ownerDocument.defaultView, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
    element.dispatchEvent(click);
};

