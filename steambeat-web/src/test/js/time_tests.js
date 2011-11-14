var TimeTests = new TestCase("TimeTests");

TimeTests.prototype = {

    testCanAddAnHour: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldHours = date.getUTCHours();

        date.plusHour(1);

        var newHours = date.getUTCHours();
        assertEquals(oldHours + 1, newHours);
    },

    testCanSubtractAnHour: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldHours = date.getUTCHours();

        date.minusHour(1);

        var newHours = date.getUTCHours();
        assertEquals(oldHours - 1, newHours);
    },

    testCanAddMultipleHours: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldHours = date.getUTCHours();

        date.plusHour(1);
        date.plusHour(3);
        date.plusHour(5);

        var newHours = date.getUTCHours();
        assertEquals(oldHours + 9, newHours);
    },

    testCanSubtractMultipleHours: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldHours = date.getUTCHours();

        date.minusHour(1);
        date.minusHour(3);
        date.minusHour(5);

        var newHours = date.getUTCHours();
        assertEquals(oldHours - 9, newHours);
    },

    testCanAddMoreThan24Hours: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldDay = date.getDate();
        var oldHours = date.getUTCHours();

        date.plusHour(48);

        var newHours = date.getUTCHours();
        var newDay = date.getDate();
        assertEquals(oldHours, newHours);
        assertEquals(oldDay + 2, newDay);
    },

    testCanSubtractMoreThan24Hours: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldDay = date.getDate();
        var oldHours = date.getUTCHours();

        date.minusHour(48);

        var newHours = date.getUTCHours();
        var newDay = date.getDate();
        assertEquals(oldHours, newHours);
        assertEquals(oldDay - 2, newDay);
    },

    testCanAddADay: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldDay = date.getDate();

        date.plusDay(1);

        var newDay = date.getDate();
        assertEquals(oldDay + 1, newDay);
    },

    testCanAddMultipleDays: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldDay = date.getDate();

        date.plusDay(1);
        date.plusDay(3);
        date.plusDay(5);

        var newDay = date.getDate();
        assertEquals(oldDay + 9, newDay);
    },

    testCanAddMoreThan31Days: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldMonth = date.getMonth();

        date.plusDay(40);

        var newMonth = date.getMonth();
        assertEquals(oldMonth + 1, newMonth);
    },

    testCanSubtractADay: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldDay = date.getDate();

        date.minusDay(1);

        var newDay = date.getDate();
        assertEquals(oldDay - 1, newDay);
    },

    testCanSubtractMultipleDays: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldDay = date.getDate();

        date.minusDay(1);
        date.minusDay(3);
        date.minusDay(5);

        var newDay = date.getDate();
        assertEquals(oldDay - 9, newDay);
    },

    testCanSubtractMoreThan31Days: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldMonth = date.getMonth();

        date.minusDay(40);

        var newMonth = date.getMonth();
        assertEquals(oldMonth - 1, newMonth);
    },

    testCanAddAMonth: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldMonth = date.getMonth();

        date.plusMonth(1);

        var newMonth = date.getMonth();
        assertEquals(oldMonth + 1, newMonth);
    },

    testCanAddMultipleMonths: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldMonth = date.getMonth();

        date.plusMonth(1);
        date.plusMonth(3);

        var newMonth = date.getMonth();
        assertEquals(oldMonth + 4, newMonth);
    },

    testCanAddMoreThan12Months: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldYear = date.getYear();

        date.plusMonth(13);

        var newYear = date.getYear();
        assertEquals(oldYear + 1, newYear);
    },

    testCanSubtractAMonth: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldMonth = date.getMonth();

        date.minusMonth(1);

        var newMonth = date.getMonth();
        assertEquals(oldMonth - 1, newMonth);
    },

    testCanSubtractMultipleMonths: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldMonth = date.getMonth();

        date.minusMonth(1);
        date.minusMonth(3);

        var newMonth = date.getMonth();
        assertEquals(oldMonth - 4, newMonth);
    },

    testCanSubtractMoreThan12Months: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldYear = date.getYear();

        date.minusMonth(13);

        var newYear = date.getYear();
        assertEquals(oldYear - 1, newYear);
    },

    testCanAddAYear: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldYear = date.getYear();

        date.plusYear(1);

        var newYear = date.getYear();
        assertEquals(oldYear + 1, newYear);
    },

    testCanAddMultipleYears: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldYear = date.getYear();

        date.plusYear(1);
        date.plusYear(3);
        date.plusYear(5);

        var newYear = date.getYear();
        assertEquals(oldYear + 9, newYear);
    },

    testCanAddMoreThan100Years: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldYear = date.getYear();

        date.plusYear(150);

        var newYear = date.getYear();
        assertEquals(oldYear + 150, newYear);
    },

    testCanSubtractAYear: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldYear = date.getYear();

        date.minusYear(1);

        var newYear = date.getYear();
        assertEquals(oldYear - 1, newYear);
    },

    testCanSubtractMultipleYears: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldYear = date.getYear();

        date.minusYear(1);
        date.minusYear(3);
        date.minusYear(5);

        var newYear = date.getYear();
        assertEquals(oldYear - 9, newYear);
    },

    testCanSubtractMoreThan100Years: function() {
        var date = new Date(2011,6,20,12,0,0);
        var oldYear = date.getYear();

        date.minusYear(150);

        var newYear = date.getYear();
        assertEquals(oldYear - 150, newYear);
    },

    testIsLeap: function() {
        var nonLeapDate = new Date(2011,1,20,12,0,0);
        var leapDate = new Date(2008,1,20,12,0,0);

        var responseFor2011 = nonLeapDate.isLeap()
        var responseFor2008 = leapDate.isLeap()

        assertSame(false, responseFor2011);
        assertSame(true, responseFor2008);
    },

    testCanFindLastDayOfJanuary2011: function() {
        var january = new Date(2011,0,10);

        var lastDay = january.lastDayOfMonth();

        assertEquals(31, lastDay);
    },

    testCanFindLastDayOfApril2011: function() {
        var april = new Date(2011,3,10);

        var lastDay = april.lastDayOfMonth();

        assertEquals(30, lastDay);
    },

    testCanFindLastDayOfFebruary2011: function() {
        var february2011 = new Date(2011,1,10);

        var lastDay = february2011.lastDayOfMonth();

        assertEquals(28, lastDay);
    },

    testCanFindLastDayOfFebruary2008: function() {
        var february2008 = new Date(2008,1,10);

        var lastDay = february2008.lastDayOfMonth();

        assertEquals(29, lastDay);
    },

    testCanGetHourInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCHours(date.getUTCHours(),0,0,0);
        end.setUTCHours(date.getUTCHours(),59,59,999);

        var interval = date.interval("hour");

        assertEquals(start.getTime(), interval.startTime);
        assertEquals(end.getTime(), interval.endTime);
        assertSame("hour", interval.granularity);
    },

    testCanGetDayInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCDate(date.getUTCDate());
        start.setUTCHours(0,0,0,0);
        end.setUTCDate(date.getUTCDate());
        end.setUTCHours(23,59,59,999);

        var interval = date.interval("day");

        assertEquals(start.getTime(), interval.startTime);
        assertEquals(end.getTime(), interval.endTime);
        assertSame("day", interval.granularity);
    },

    testCanGetMonthInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCMonth(date.getUTCMonth());
        start.setUTCDate(1);
        start.setUTCHours(0,0,0,0);
        end.setUTCMonth(date.getUTCMonth());
        end.setUTCDate(30);
        end.setUTCHours(23,59,59,999);

        var interval = date.interval("month");

        assertEquals(start.getTime(), interval.startTime);
        assertEquals(end.getTime(), interval.endTime);
        assertSame("month", interval.granularity);
    },

    testCanGetYearInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCFullYear(date.getUTCFullYear());
        start.setUTCMonth(0);
        start.setUTCDate(1);
        start.setUTCHours(0,0,0,0);
        end.setUTCFullYear(date.getUTCFullYear());
        end.setUTCMonth(11);
        end.setUTCDate(31);
        end.setUTCHours(23,59,59,999);

        var interval = date.interval("year");

        assertEquals(start.getTime(), interval.startTime);
        assertEquals(end.getTime(), interval.endTime);
        assertSame("year", interval.granularity);
    },

    testIsDateContainedInHourInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var startDate = new Date(date.interval("hour").startTime);
        var endDate = new Date(date.interval("hour").endTime);
        var badDate = new Date(date);
        badDate.plusHour(1);
        var interval = date.interval("hour");

        var isContained = interval.containsDate(date);
        var isStartTimeContained = interval.containsDate(startDate);
        var isEndTimeContained = interval.containsDate(endDate);
        var isNotContained = interval.containsDate(badDate);

        assertSame(true, isContained);
        assertSame(true, isStartTimeContained);
        assertSame(false, isEndTimeContained);
        assertSame(false, isNotContained);
    },

    testIsDateContainedInDayInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var startDate = new Date(date.interval("day").startTime);
        var endDate = new Date(date.interval("day").endTime);
        var badDate = new Date(date);
        badDate.plusDay(1);
        var interval = date.interval("day");

        var isContained = interval.containsDate(date);
        var isStartTimeContained = interval.containsDate(startDate);
        var isEndTimeContained = interval.containsDate(endDate);
        var isNotContained = interval.containsDate(badDate);

        assertSame(true, isContained);
        assertSame(true, isStartTimeContained);
        assertSame(false, isEndTimeContained);
        assertSame(false, isNotContained);
    },

    testIsDateContainedInMonthInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var startDate = new Date(date.interval("month").startTime);
        var endDate = new Date(date.interval("month").endTime);
        var badDate = new Date(date);
        badDate.minusMonth(1);
        var interval = date.interval("month");

        var isContained = interval.containsDate(date);
        var isStartTimeContained = interval.containsDate(startDate);
        var isEndTimeContained = interval.containsDate(endDate);
        var isNotContained = interval.containsDate(badDate);

        assertSame(true, isContained);
        assertSame(true, isStartTimeContained);
        assertSame(false, isEndTimeContained);
        assertSame(false, isNotContained);
    },

    testIsDateContainedInYearInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var startDate = new Date(date.interval("year").startTime);
        var endDate = new Date(date.interval("year").endTime);
        var badDate = new Date(date);
        badDate.minusYear(1);
        var interval = date.interval("year");

        var isContained = interval.containsDate(date);
        var isStartTimeContained = interval.containsDate(startDate);
        var isEndTimeContained = interval.containsDate(endDate);
        var isNotContained = interval.containsDate(badDate);

        assertSame(true, isContained);
        assertSame(true, isStartTimeContained);
        assertSame(false, isEndTimeContained);
        assertSame(false, isNotContained);
    },

    testIsTimeContainedInHourInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var startDate = new Date(date.interval("hour").startTime);
        var endDate = new Date(date.interval("hour").endTime);
        var badDate = new Date(date);
        badDate.plusHour(1);
        var interval = date.interval("hour");

        var isContained = interval.containsTime(date.getTime());
        var isStartTimeContained = interval.containsTime(startDate.getTime());
        var isEndTimeContained = interval.containsTime(endDate.getTime());
        var isNotContained = interval.containsTime(badDate.getTime());

        assertSame(true, isContained);
        assertSame(true, isStartTimeContained);
        assertSame(false, isEndTimeContained);
        assertSame(false, isNotContained);
    },


    testIsTimeContainedInDayInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var startDate = new Date(date.interval("day").startTime);
        var endDate = new Date(date.interval("day").endTime);
        var badDate = new Date(date);
        badDate.plusDay(1);
        var interval = date.interval("day");

        var isContained = interval.containsTime(date.getTime());
        var isStartTimeContained = interval.containsTime(startDate.getTime());
        var isEndTimeContained = interval.containsTime(endDate.getTime());
        var isNotContained = interval.containsTime(badDate.getTime());

        assertSame(true, isContained);
        assertSame(true, isStartTimeContained);
        assertSame(false, isEndTimeContained);
        assertSame(false, isNotContained);
    },

    testIsTimeContainedInMonthInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var startDate = new Date(date.interval("month").startTime);
        var endDate = new Date(date.interval("month").endTime);
        var badDate = new Date(date);
        badDate.minusMonth(1);
        var interval = date.interval("month");

        var isContained = interval.containsTime(date.getTime());
        var isStartTimeContained = interval.containsTime(startDate.getTime());
        var isEndTimeContained = interval.containsTime(endDate.getTime());
        var isNotContained = interval.containsTime(badDate.getTime());

        assertSame(true, isContained);
        assertSame(true, isStartTimeContained);
        assertSame(false, isEndTimeContained);
        assertSame(false, isNotContained);
    },

    testIsTimeContainedInYearInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var startDate = new Date(date.interval("year").startTime);
        var endDate = new Date(date.interval("year").endTime);
        var badDate = new Date(date);
        badDate.minusYear(1);
        var interval = date.interval("year");

        var isContained = interval.containsTime(date.getTime());
        var isStartTimeContained = interval.containsTime(startDate.getTime());
        var isEndTimeContained = interval.containsTime(endDate.getTime());
        var isNotContained = interval.containsTime(badDate.getTime());

        assertSame(true, isContained);
        assertSame(true, isStartTimeContained);
        assertSame(false, isEndTimeContained);
        assertSame(false, isNotContained);
    },

    testCanCreateNewCopyOfDate: function() {
        var date1 = new Date(2011,3,3,12,30,0,000);
        var date1OldTime = date1.getTime();
        var date2 = new Date(date1);
        var date2OldTime = date2.getTime();

        date2.plusHour(1);

        var date1NewTime = date1.getTime();
        var date2NewTime = date2.getTime();
        assertEquals(date1OldTime, date1NewTime);
        assertNotEquals(date2OldTime, date2NewTime);
    },

    testCanGetNextHourIntervalFromDate: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCHours(date.getUTCHours(),0,0,0);
        end.setUTCHours(date.getUTCHours(),59,59,999);
        start.plusHour(1);
        end.plusHour(1);

        var interval = date.nextInterval("hour");

        assertEquals(start.getTime(), interval.startTime);
        assertEquals(end.getTime(), interval.endTime);
    },


    testCanGetNextDayIntervalFromDate: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCDate(date.getUTCDate());
        start.setUTCHours(0,0,0,0);
        end.setUTCDate(date.getUTCDate());
        end.setUTCHours(23,59,59,999);
        start.plusDay(1);
        end.plusDay(1);

        var interval = date.nextInterval("day");

        assertEquals(start.getTime(), interval.startTime);
        assertEquals(end.getTime(), interval.endTime);
    },

    testCanGetNextMonthIntervalFromDate: function() {
        var date = new Date(2011,6,3,12,30,0,000);
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCMonth(date.getUTCMonth());
        start.setUTCDate(1);
        start.setUTCHours(0,0,0,0);
        end.setUTCMonth(date.getUTCMonth());
        end.setUTCDate(31);
        end.setUTCHours(23,59,59,999);
        start.plusMonth(1);
        end.plusMonth(1);

        var interval = date.nextInterval("month");

        assertEquals(start.getTime(), interval.startTime);
        assertEquals(end.getTime(), interval.endTime);
    },

    testCanGetNextYearIntervalFromDate: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCFullYear(date.getUTCFullYear());
        start.setUTCMonth(0);
        start.setUTCDate(1);
        start.setUTCHours(0,0,0,0);
        end.setUTCFullYear(date.getUTCFullYear());
        end.setUTCMonth(11);
        end.setUTCDate(31);
        end.setUTCHours(23,59,59,999);
        start.plusYear(1);
        end.plusYear(1);

        var interval = date.nextInterval("year");

        assertEquals(start.getTime(), interval.startTime);
        assertEquals(end.getTime(), interval.endTime);
    },

    testCanGetPreviousHourIntervalFromDate: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCHours(date.getUTCHours(),0,0,0);
        end.setUTCHours(date.getUTCHours(),59,59,999);
        start.minusHour(1);
        end.minusHour(1);

        var interval = date.previousInterval("hour");

        assertEquals(start.getTime(), interval.startTime);
        assertEquals(end.getTime(), interval.endTime);
    },


    testCanGetPreviousDayIntervalFromDate: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCDate(date.getUTCDate());
        start.setUTCHours(0,0,0,0);
        end.setUTCDate(date.getUTCDate());
        end.setUTCHours(23,59,59,999);
        start.minusDay(1);
        end.minusDay(1);

        var interval = date.previousInterval("day");

        assertEquals(start.getTime(), interval.startTime);
        assertEquals(end.getTime(), interval.endTime);
    },

    testCanGetPreviousMonthIntervalFromDate: function() {
        var date = new Date(2011,7,3,12,30,0,000);
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCMonth(date.getUTCMonth());
        start.setUTCDate(1);
        start.setUTCHours(0,0,0,0);
        end.setUTCMonth(date.getUTCMonth());
        end.setUTCDate(31);
        end.setUTCHours(23,59,59,999);
        start.minusMonth(1);
        end.minusMonth(1);

        var interval = date.previousInterval("month");

        assertEquals(start.getTime(), interval.startTime);
        assertEquals(end.getTime(), interval.endTime);
    },

    testCanGetPreviousYearIntervalFromDate: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCFullYear(date.getUTCFullYear());
        start.setUTCMonth(0);
        start.setUTCDate(1);
        start.setUTCHours(0,0,0,0);
        end.setUTCFullYear(date.getUTCFullYear());
        end.setUTCMonth(11);
        end.setUTCDate(31);
        end.setUTCHours(23,59,59,999);
        start.minusYear(1);
        end.minusYear(1);

        var interval = date.previousInterval("year");

        assertEquals(start.getTime(), interval.startTime);
        assertEquals(end.getTime(), interval.endTime);
    },


    testCanGetNextHourIntervalFromInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var interval = date.interval("hour");
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCHours(date.getUTCHours(),0,0,0);
        end.setUTCHours(date.getUTCHours(),59,59,999);
        start.plusHour(1);
        end.plusHour(1);

        var nextInterval = interval.nextInterval();

        assertEquals(start.getTime(), nextInterval.startTime);
        assertEquals(end.getTime(), nextInterval.endTime);
    },

    testCanGetDistantNextHourIntervalFromInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var interval = date.interval("hour");
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCHours(date.getUTCHours(),0,0,0);
        end.setUTCHours(date.getUTCHours(),59,59,999);
        start.plusHour(3);
        end.plusHour(3);

        var nextInterval = interval.nextInterval(3);

        assertEquals(start.getTime(), nextInterval.startTime);
        assertEquals(end.getTime(), nextInterval.endTime);
    },

    testCanGetNextDayIntervalFromInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var interval = date.interval("day");
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCDate(date.getUTCDate());
        start.setUTCHours(0,0,0,0);
        end.setUTCDate(date.getUTCDate());
        end.setUTCHours(23,59,59,999);
        start.plusDay(1);
        end.plusDay(1);

        var nextInterval = interval.nextInterval();

        assertEquals(start.getTime(), nextInterval.startTime);
        assertEquals(end.getTime(), nextInterval.endTime);
    },

    testCanGetNextMonthIntervalFromInterval: function() {
        var date = new Date(2011,6,3,12,30,0,000);
        var interval = date.interval("month");
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCMonth(date.getUTCMonth());
        start.setUTCDate(1);
        start.setUTCHours(0,0,0,0);
        end.setUTCMonth(date.getUTCMonth());
        end.setUTCDate(31);
        end.setUTCHours(23,59,59,999);
        start.plusMonth(1);
        end.plusMonth(1);

        var nextInterval = interval.nextInterval();

        assertEquals(start.getTime(), nextInterval.startTime);
        assertEquals(end.getTime(), nextInterval.endTime);
    },

    testCanGetNextYearIntervalFromInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var interval = date.interval("year");
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCFullYear(date.getUTCFullYear());
        start.setUTCMonth(0);
        start.setUTCDate(1);
        start.setUTCHours(0,0,0,0);
        end.setUTCFullYear(date.getUTCFullYear());
        end.setUTCMonth(11);
        end.setUTCDate(31);
        end.setUTCHours(23,59,59,999);
        start.plusYear(1);
        end.plusYear(1);

        var nextInterval = interval.nextInterval();

        assertEquals(start.getTime(), nextInterval.startTime);
        assertEquals(end.getTime(), nextInterval.endTime);
    },

    testCanGetPreviousHourIntervalFromInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var interval = date.interval("hour");
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCHours(date.getUTCHours(),0,0,0);
        end.setUTCHours(date.getUTCHours(),59,59,999);
        start.minusHour(1);
        end.minusHour(1);

        var previousInterval = interval.previousInterval();

        assertEquals(start.getTime(), previousInterval.startTime);
        assertEquals(end.getTime(), previousInterval.endTime);
    },

    testCanGetDistantPreviousHourIntervalFromInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var interval = date.interval("hour");
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCHours(date.getUTCHours(),0,0,0);
        end.setUTCHours(date.getUTCHours(),59,59,999);
        start.minusHour(3);
        end.minusHour(3);

        var previousInterval = interval.previousInterval(3);

        assertEquals(start.getTime(), previousInterval.startTime);
        assertEquals(end.getTime(), previousInterval.endTime);
    },

    testCanGetPreviousDayIntervalFromInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var interval = date.interval("day");
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCDate(date.getUTCDate());
        start.setUTCHours(0,0,0,0);
        end.setUTCDate(date.getUTCDate());
        end.setUTCHours(23,59,59,999);
        start.minusDay(1);
        end.minusDay(1);

        var previousInterval = interval.previousInterval();

        assertEquals(start.getTime(), previousInterval.startTime);
        assertEquals(end.getTime(), previousInterval.endTime);
    },

    testCanGetPreviousMonthIntervalFromInterval: function() {
        var date = new Date(2011,7,3,12,30,0,000);
        var interval = date.interval("month");
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCMonth(date.getUTCMonth());
        start.setUTCDate(1);
        start.setUTCHours(0,0,0,0);
        end.setUTCMonth(date.getUTCMonth());
        end.setUTCDate(31);
        end.setUTCHours(23,59,59,999);
        start.minusMonth(1);
        end.minusMonth(1);

        var previousInterval = interval.previousInterval();

        assertEquals(start.getTime(), previousInterval.startTime);
        assertEquals(end.getTime(), previousInterval.endTime);
    },

    testCanGetPreviousYearIntervalFromInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var interval = date.interval("year");
        var start = new Date(date);
        var end = new Date(date);
        start.setUTCFullYear(date.getUTCFullYear());
        start.setUTCMonth(0);
        start.setUTCDate(1);
        start.setUTCHours(0,0,0,0);
        end.setUTCFullYear(date.getUTCFullYear());
        end.setUTCMonth(11);
        end.setUTCDate(31);
        end.setUTCHours(23,59,59,999);
        start.minusYear(1);
        end.minusYear(1);

        var previousInterval = interval.previousInterval();

        assertEquals(start.getTime(), previousInterval.startTime);
        assertEquals(end.getTime(), previousInterval.endTime);
    },
    
    testCanGetHumanReadableHourReference: function() {
        var date = new Date(2011,3,3,12,30,0,000);

        var text = date.readableHour();

        assertSame("12H", text);
    },

    testCanGetReadableUTCDayInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);

        var text = date.readableDay();

        assertSame("3 Apr", text);
    },

    testCanGetReadableUTCMonthInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);

        var text = date.readableMonth();

        assertSame("Apr", text);
    },

    testCanGetReadableUTCYearInterval: function() {
        var date = new Date(2011,3,3,12,30,0,000);

        var text = date.readableYear();

        assertEquals(2011, text);
    },

    testCanChainPlusHour: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var oldHours = date.getUTCHours();

        date.plusHour(1).plusHour(1);

        var hours = date.getUTCHours();
        assertEquals(oldHours + 2, hours);
    },

    testCanChainPlusDay: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var oldDays = date.getDate();

        date.plusDay(1).plusDay(1);

        var days = date.getDate();
        assertEquals(oldDays + 2, days);
    },

    testCanChainPlusMonth: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var oldMonths = date.getMonth();

        date.plusMonth(1).plusMonth(1);

        var months = date.getMonth();
        assertEquals(oldMonths + 2, months);
    },

    testCanChainPlusYear: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var oldYears = date.getYear();

        date.plusYear(1).plusYear(1);

        var years = date.getYear();
        assertEquals(oldYears + 2, years);
    },

    testCanChainMinusHour: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var oldHours = date.getUTCHours();

        date.minusHour(1).minusHour(1);

        var hours = date.getUTCHours();
        assertEquals(oldHours - 2, hours);
    },

    testCanChainMinusDay: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var oldDays = date.getDate();

        date.minusDay(1).minusDay(1);

        var days = date.getDate();
        assertEquals(oldDays - 2, days);
    },

    testCanChainMinusMonth: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var oldMonths = date.getMonth();

        date.minusMonth(1).minusMonth(1);

        var months = date.getMonth();
        assertEquals(oldMonths - 2, months);
    },

    testCanChainMinusYear: function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var oldYears = date.getYear();

        date.minusYear(1).minusYear(1);

        var years = date.getYear();
        assertEquals(oldYears - 2, years);
    },

    testCanChainOtherCommands: function() {
        var date1 = new Date(2011,3,3,12,30,0,000);
        var date2 = new Date(2011,3,3,12,30,0,000);

        var time = date1.plusHour(1).getTime();

        assertEquals(date2.getTime() + 3600000, time);
    },

    testCanGetHourIntervalForTime:  function() {
        var date = new Date(2011,3,3,12,30,0,000);
        var interval = date.interval("hour");

        var timeInterval = Date.intervalForTime(date.getTime(), "hour");

        assertEquals(timeInterval.startTime, interval.startTime);
        assertEquals(timeInterval.endTime, interval.endTime);
    },

    testCanCompareIntervals: function() {
        var date = new Date(2011,7,3,12,30,0,000);
        var firstHourInterval = date.interval("hour");
        var secondHourInterval = date.interval("hour");
        var monthInterval = date.interval("month");
        date.plusHour(20);
        var thirdHourInterval = date.interval("hour");

        var first = firstHourInterval.equals(secondHourInterval);
        var second = firstHourInterval.equals(thirdHourInterval);
        var third = firstHourInterval.equals(monthInterval);

        assertTrue(first);
        assertFalse(second);
        assertFalse(third);
    },

    testCanGetNextGranularityFromHour: function() {
        var nextGranularity = Date.nextGranularity("hour");

        assertSame("day", nextGranularity);
    },

    testCanGetNextGranularityFromDay: function() {
        var nextGranularity = Date.nextGranularity("day");

        assertSame("month", nextGranularity);
    },

    testCanGetNextGranularityFromMonth: function() {
        var nextGranularity = Date.nextGranularity("month");

        assertSame("year", nextGranularity);
    },

    testNextGranularityFromUpperGranularityIsTheSame: function() {
        var nextGranularity = Date.nextGranularity("year");

        assertSame("year", nextGranularity);
    },

    testCannotFindNextGranularityFromUnknownGranularity: function() {
        var nextGranularity = Date.nextGranularity("unknown");

        assertNull(nextGranularity);
    },

    testCanGetPreviousGranularityFromYear: function() {
        var previousGranularity = Date.previousGranularity("year");

        assertSame("month", previousGranularity);
    },

    testCanGetPreviousGranularityFromMonth: function() {
        var previousGranularity = Date.previousGranularity("month");

        assertSame("day", previousGranularity);
    },

    testCanGetPreviousGranularityFromDay: function() {
        var previousGranularity = Date.previousGranularity("day");

        assertSame("hour", previousGranularity);
    },

    testPreviousGranularityFromLowestGranularityIsTheSame: function() {
        var previousGranularity = Date.previousGranularity("hour");

        assertSame("hour", previousGranularity);
    },

    testCannotGetPreviousGranularityFromUnknown: function() {
        var previousGranularity = Date.previousGranularity("unknown");

        assertNull(previousGranularity);
    },

    testCanGetNewGranularityIntervalFromInterval: function() {
        var date = new Date();
        var hourInterval = date.interval("hour");
        var dayInterval = date.interval("day");

        var newInterval = hourInterval.toInterval("day");

        assertTrue(newInterval.equals(dayInterval));
    },

    testIsIntervalBefore: function() {
        var date = new Date(2011,7,3,12,30,0,000);
        var first = date.interval("hour");
        var second = date.plusHour(1).interval("hour");
        var third = date.plusHour(1).interval("hour");
        var day = date.interval("day");

        assertTrue(first.isBefore(second));
        assertTrue(second.isBefore(third));
        assertFalse(third.isBefore(first));
        assertFalse(third.isBefore(second));
        assertFalse(first.isBefore(day));
    },

    testIsIntervalAfter: function() {
        var date = new Date(2011,7,3,12,30,0,000);
        var day = date.interval("day");
        var first = date.interval("hour");
        var second = date.plusHour(1).interval("hour");
        var third = date.plusHour(1).interval("hour");

        assertFalse(first.isAfter(second));
        assertFalse(second.isAfter(third));
        assertTrue(third.isAfter(first));
        assertTrue(third.isAfter(second));
        assertFalse(second.isAfter(day));
    },

    testCanGetHourIntervalToString: function() {
        var date = new Date(2011,7,3,12,30,0,000);
        var hourInterval = date.interval("hour");

        assertSame("12H", hourInterval.toString());
    },

    testCanGetDayIntervalToString: function() {
        var date = new Date(2011,7,3,12,30,0,000);
        var hourInterval = date.interval("day");

        assertSame("3 Aug", hourInterval.toString());
    },

    testCanGetMonthIntervalToString: function() {
        var date = new Date(2011,7,3,12,30,0,000);
        var hourInterval = date.interval("month");

        assertSame("Aug", hourInterval.toString());
    },

    testCanGetYearIntervalToString: function() {
        var date = new Date(2011,7,3,12,30,0,000);
        var hourInterval = date.interval("year");

        assertSame("2011", hourInterval.toString());
    }
};