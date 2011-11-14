package com.steambeat.domain.statistics;

import org.joda.time.*;

public enum Granularity {
    hour {
        @Override
        public Interval intervalFor(DateTime date) {
            return date.hourOfDay().toInterval();
        }

        @Override
        public Interval intervalFor(final DateTime start, final DateTime end) {
            return new Interval(start.hourOfDay().roundFloorCopy(), end.hourOfDay().roundCeilingCopy());
        }
    }, day {
        @Override
        public Interval intervalFor(DateTime date) {
            return date.dayOfMonth().toInterval();
        }

        @Override
        public Interval intervalFor(final DateTime start, final DateTime end) {
            return new Interval(start.dayOfMonth().roundFloorCopy(), end.dayOfMonth().roundCeilingCopy());
        }
    }, month {
        @Override
        public Interval intervalFor(DateTime date) {
            return date.monthOfYear().toInterval();
        }

        @Override
        public Interval intervalFor(final DateTime start, final DateTime end) {
            return new Interval(start.monthOfYear().roundFloorCopy(), end.monthOfYear().roundCeilingCopy());
        }
    }, year {
        @Override
        public Interval intervalFor(DateTime date) {
            return date.year().toInterval();
        }

        @Override
        public Interval intervalFor(final DateTime start, final DateTime end) {
            return new Interval(start.year().roundFloorCopy(), end.year().roundCeilingCopy());
        }
    }, all {
        @Override
        public Interval intervalFor(DateTime date) {
            return date.centuryOfEra().toInterval();
        }

        @Override
        public Interval intervalFor(final DateTime start, final DateTime end) {
            return start.centuryOfEra().toInterval();
        }
    };

    public abstract Interval intervalFor(DateTime date);

    public abstract Interval intervalFor(DateTime start, DateTime end);
}
