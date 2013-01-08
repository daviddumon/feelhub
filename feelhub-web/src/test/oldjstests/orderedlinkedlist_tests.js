var OrderedLinkedListTests = new TestCase("LinkedListTests");

OrderedLinkedListTests.prototype = {

    testCanCreateLinkedList: function () {
        var list = new OrderedLinkedList();

        assertNotUndefined(list.currentNode);
        assertNull(list.currentNode);
        assertNotUndefined(list.startNode);
        assertNull(list.startNode);
        assertNotUndefined(list.endNode);
        assertNull(list.endNode);
    },

    testHasASortableProperty: function () {
        var list = new OrderedLinkedList("property");

        assertNotUndefined(list.sortableProperty);
        assertNotNull(list.sortableProperty);
        assertSame("property", list.sortableProperty);
    },

    testReturnCurrentNullIfEmpty: function () {
        var list = new OrderedLinkedList();

        assertNull(list.current());
    },

    testCanAdd: function () {
        var interval = new Date().interval("hour");
        var list = new OrderedLinkedList("property");
        var graph = new FakeGraph(interval);

        list.add(graph);

        assertEquals(1, list.length);
        assertSame(graph, list.startNode.data);
    },

    testCanAddTwoElements: function () {
        var interval = new Date().interval("hour");
        var list = new OrderedLinkedList("interval");
        var first = new FakeGraph(interval);
        var second = new FakeGraph(interval.nextInterval());

        list.add(first);
        list.add(second);

        assertEquals(2, list.length);
        assertSame(first, list.startNode.data);
        assertSame(second, list.endNode.data);
    },

    testAddSetTheCurrentNode: function () {
        var interval = new Date().interval("hour");
        var list = new OrderedLinkedList();
        var graph = new FakeGraph(interval);

        list.add(graph);

        assertSame(graph, list.current());
    },

    testAddSetTheCurrentNodeOnlyIfListEmpty: function () {
        var interval = new Date().interval("hour");
        var list = new OrderedLinkedList("interval");
        var first = new FakeGraph(interval);
        var second = new FakeGraph(interval);

        list.add(first);
        list.add(second);

        assertSame(first, list.current());
    },

    testCanGetNextFromCurrent: function () {
        var interval = new Date().interval("hour");
        var list = new OrderedLinkedList("interval");
        var first = new FakeGraph(interval);
        var second = new FakeGraph(interval.nextInterval());

        list.add(first);
        list.add(second);

        assertSame(first, list.current());
        assertSame(second, list.next());
    },

    testCanFindNodeWithSortableProperty: function () {
        var firstInterval = new Date().minusHour(1).interval("hour");
        var secondInterval = new Date().interval("hour");
        var first = new FakeGraph(firstInterval);
        var second = new FakeGraph(secondInterval);
        var list = new OrderedLinkedList("interval");
        list.add(first);
        list.add(second);

        var fakegraph = list.find(secondInterval);

        assertSame(fakegraph, second);
    },

    testReturnNullIfNotFound: function () {
        var firstInterval = new Date().minusHour(1).interval("hour");
        var secondInterval = new Date().interval("hour");
        var first = new FakeGraph(firstInterval);
        var second = new FakeGraph(secondInterval);
        var list = new OrderedLinkedList("interval");
        list.add(first);
        list.add(second);

        assertNull(list.find(new Date().plusDay(1).interval("hour")));
    },

    testCanChangeCurrent: function () {
        var firstInterval = new Date().minusHour(1).interval("hour");
        var secondInterval = new Date().interval("hour");
        var first = new FakeGraph(firstInterval);
        var second = new FakeGraph(secondInterval);
        var list = new OrderedLinkedList("interval");
        list.add(first);
        list.add(second);

        list.setCurrent(second);

        assertSame(second, list.current());
    },

    testCanInsertPrevious: function () {
        var firstInterval = new Date().minusHour(1).interval("hour");
        var secondInterval = new Date().interval("hour");
        var thirdInterval = new Date().plusHour(1).interval("hour");
        var first = new FakeGraph(firstInterval);
        var second = new FakeGraph(secondInterval);
        var third = new FakeGraph(thirdInterval);
        var list = new OrderedLinkedList("interval");

        list.add(first);
        list.add(second);
        list.add(third);
        list.setCurrent(second);

        assertSame(second, list.current());
        assertSame(third, list.next());
        assertSame(first, list.previous());
    },

    testSortWhenInsertingAnElement: function () {
        var firstInterval = new Date().plusHour(1).interval("hour");
        var secondInterval = new Date().interval("hour");
        var thirdInterval = new Date().minusHour(1).interval("hour");
        var first = new FakeGraph(firstInterval);
        var second = new FakeGraph(secondInterval);
        var third = new FakeGraph(thirdInterval);
        var list = new OrderedLinkedList("interval");

        list.add(first);
        list.add(second);
        list.add(third);

        assertSame(first, list.current());
        assertSame(second, list.previous());
        assertSame(null, list.next());
    },

    testSortWhenInsertingALotOfElements: function () {
        var firstInterval = new Date().interval("hour");
        var secondInterval = new Date().plusHour(10).interval("hour");
        var thirdInterval = new Date().minusHour(5).interval("hour");
        var fourthInterval = new Date().plusHour(5).interval("hour");
        var first = new FakeGraph(firstInterval);
        var second = new FakeGraph(secondInterval);
        var third = new FakeGraph(thirdInterval);
        var fourth = new FakeGraph(fourthInterval);
        var list = new OrderedLinkedList("interval");

        list.add(first);
        list.add(second);
        list.add(third);
        list.add(fourth);

        assertSame(first, list.current());
        assertSame(third, list.previous());
        assertSame(fourth, list.next());
    },

    testSortWhenInsertingALotOfElementsAndSettingNewCurrent: function () {
        var firstInterval = new Date().interval("hour");
        var secondInterval = new Date().plusHour(10).interval("hour");
        var thirdInterval = new Date().minusHour(5).interval("hour");
        var fourthInterval = new Date().plusHour(5).interval("hour");
        var first = new FakeGraph(firstInterval);
        var second = new FakeGraph(secondInterval);
        var third = new FakeGraph(thirdInterval);
        var fourth = new FakeGraph(fourthInterval);
        var list = new OrderedLinkedList("interval");

        list.add(first);
        list.add(second);
        list.add(third);
        list.add(fourth);

        list.setCurrent(fourth);
        assertSame(fourth, list.current());
        assertSame(first, list.previous());
        assertSame(second, list.next());

        list.setCurrent(third);
        assertSame(third, list.current());
        assertSame(null, list.previous());
        assertSame(first, list.next());
    },

    testCanSetPreviousAsCurrentNode: function () {
        var firstInterval = new Date().minusHour(1).interval("hour");
        var secondInterval = new Date().interval("hour");
        var thirdInterval = new Date().plusHour(1).interval("hour");
        var first = new FakeGraph(firstInterval);
        var second = new FakeGraph(secondInterval);
        var third = new FakeGraph(thirdInterval);
        var list = new OrderedLinkedList("interval");
        list.add(first);
        list.add(second);
        list.add(third);

        assertSame(first, list.current());
        assertSame(second, list.next());
        assertSame(null, list.previous());

        list.setCurrent(second);
        assertSame(second, list.current());
        assertSame(third, list.next());
        assertSame(first, list.previous());

        list.setPreviousAsCurrentNode();
        assertSame(first, list.current());
    },

    testCanSetNextAsCurrentNide: function () {
        var firstInterval = new Date().minusHour(1).interval("hour");
        var secondInterval = new Date().interval("hour");
        var thirdInterval = new Date().plusHour(1).interval("hour");
        var first = new FakeGraph(firstInterval);
        var second = new FakeGraph(secondInterval);
        var third = new FakeGraph(thirdInterval);
        var list = new OrderedLinkedList("interval");
        list.add(first);
        list.add(second);
        list.add(third);
        list.setCurrent(second);

        list.setNextAsCurrentNode();

        assertSame(third, list.current());
    },
};

function FakeGraph(interval) {
    this.interval = (interval !== undefined ? interval : null);

    this.equals = function (item) {
        return this.interval.equals(item.interval);
    };
};