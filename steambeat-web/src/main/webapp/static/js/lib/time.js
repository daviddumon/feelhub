/* Copyright bytedojo 2011 */
function Interval(startTime, endTime, granularity) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.granularity = granularity;

    this.containsDate = function(date) {
        return date.getTime() >= this.startTime && date.getTime() < this.endTime;
    };

    this.containsTime = function(time) {
        return time >= this.startTime && time < this.endTime;
    };

    this.nextInterval = function(value) {
        if(value == null) {
            value = 1;
        }
        var end = this.endTime;
        var interval;
        for(var i = 0; i < value; i++) {
            var date = new Date(end + 1);
            interval = date.interval(this.granularity);
            end = interval.endTime;
        }
        return interval;
    };

    this.previousInterval = function(value) {
        if(value == null) {
            value = 1;
        }
        var start = this.startTime;
        var interval;
        for(var i = 0; i < value; i++) {
            var date = new Date(start - 1);
            interval = date.interval(this.granularity);
            start = interval.startTime;
        }
        return interval;
    };

    this.equals = function(interval) {
        return interval.startTime == this.startTime
            && interval.endTime == this.endTime
            && interval.granularity == this.granularity;
    };

    this.toInterval = function(granularity) {
        return new Date(startTime).interval(granularity);
    };

    this.isBefore = function(interval) {
        return this.startTime < interval.startTime
            && this.endTime < interval.endTime;
    };

    this.isAfter = function(interval) {
        return this.startTime > interval.startTime
            && this.endTime > interval.endTime;
    };

    this.toString = function() {
        var date = new Date(this.startTime);
        switch(this.granularity) {
            case 'hour':
                return date.readableHour();
            case 'day':
                return date.readableDay();
            case 'month':
                return date.readableMonth();
            case 'year':
                return date.readableYear();
            default:
                return "";
        }
    }
}

Date.granularities = ["hour","day","month","year"];

Date.nextGranularity = function(granularity) {
    // indexOf n'est pas standard !!
    for(var i = 0; i < Date.granularities.length - 1; i++) {
        if(granularity == Date.granularities[i]) {
            return Date.granularities[++i];
        }
    }
    if(granularity == Date.granularities[Date.granularities.length - 1]) {
        return Date.granularities[Date.granularities.length - 1];
    } else {
        return null;
    }
};

Date.previousGranularity = function(granularity) {
    for(var i = Date.granularities.length - 1; i > 0; i--) {
        if(granularity == Date.granularities[i]) {
            return Date.granularities[--i];
        }
    }
    if(granularity == Date.granularities[0]) {
        return Date.granularities[0];
    } else {
        return null;
    }
};

Date.prototype.plusHour = function(hours) {
    var old = this.getUTCHours();
    this.setUTCHours(parseInt(old + hours));
    return this;
};

Date.prototype.minusHour = function(hours) {
    var old = this.getUTCHours();
    this.setUTCHours(parseInt(old - hours));
    return this;
};

Date.prototype.plusDay = function(days) {
    var old = this.getUTCDate();
    this.setUTCDate(old + days);
    return this;
};

Date.prototype.minusDay = function(days) {
    var old = this.getUTCDate();
    this.setUTCDate(old - days);
    return this;
};

Date.prototype.plusMonth = function(months) {
    var old = this.getUTCMonth();
    this.setUTCMonth(old + months);
    return this;
};

Date.prototype.minusMonth = function(months) {
    var old = this.getUTCMonth();
    this.setUTCMonth(old - months);
    return this;
};

Date.prototype.plusYear = function(years) {
    var old = this.getUTCFullYear();
    this.setUTCFullYear(old + years);
    return this;
};

Date.prototype.minusYear = function(years) {
    var old = this.getUTCFullYear();
    this.setUTCFullYear(old - years);
    return this;
};

Date.prototype.isLeap = function() {
    return new Date(this.getUTCFullYear(),1,29).getDate() == 29;
};

Date.prototype.lastDayOfMonth = function() {
    switch(this.getUTCMonth()) {
        case 0:
        case 2:
        case 4:
        case 6:
        case 7:
        case 9:
        case 11:
            return 31;
        case 3:
        case 5:
        case 8:
        case 10:
            return 30;
        case 1:
            if(this.isLeap()) {
                return 29;
            } else {
                return 28;
            }
    }
};

Date.intervalForTime = function(time, granularity) {
    var date = new Date(time);
    return date.interval(granularity);
};

Date.prototype.interval = function(granularity) {
    var startDate = new Date(this);
    var endDate = new Date(this);

    switch(granularity) {
        case 'year':
            startDate.setUTCFullYear(this.getUTCFullYear());
            startDate.setUTCMonth(0);
            startDate.setUTCDate(1);
            startDate.setUTCHours(0,0,0,0);
            endDate.setUTCFullYear(this.getUTCFullYear());
            endDate.setUTCMonth(11);
            endDate.setUTCDate(31);
            endDate.setUTCHours(23,59,59,999);
            break;
        case 'month':
            startDate.setUTCMonth(this.getUTCMonth());
            startDate.setUTCDate(1);
            startDate.setUTCHours(0,0,0,0);
            endDate.setUTCMonth(this.getUTCMonth());
            endDate.setUTCDate(this.lastDayOfMonth());
            endDate.setUTCHours(23,59,59,999);
            break;
        case 'day':
            startDate.setUTCDate(this.getUTCDate());
            startDate.setUTCHours(0,0,0,0);
            endDate.setUTCDate(this.getUTCDate());
            endDate.setUTCHours(23,59,59,999);
            break;
        case 'hour':
            startDate.setUTCHours(this.getUTCHours(),0,0,0);
            endDate.setUTCHours(this.getUTCHours(),59,59,999);
            break;
        default:
            break;
    };

    return new Interval(startDate.getTime(), endDate.getTime(), granularity);
};

Date.prototype.nextInterval = function(granularity) {
    switch(granularity) {
        case 'year':
            this.plusYear(1);
            break;
        case 'month':
            this.plusMonth(1);
            break;
        case 'day':
            this.plusDay(1);
            break;
        case 'hour':
            this.plusHour(1);
            break;
        default:
            break;
    };

    return this.interval(granularity);
};

Date.prototype.previousInterval = function(granularity) {
    switch(granularity) {
        case 'year':
            this.minusYear(1);
            break;
        case 'month':
            this.minusMonth(1);
            break;
        case 'day':
            this.minusDay(1);
            break;
        case 'hour':
            this.minusHour(1);
            break;
        default:
            break;
    };

    return this.interval(granularity);
};

Date.prototype.readableHour = function() {
    return this.getHours() + "H";
};

Date.prototype.readableDay = function() {
    return this.getDate() + " " + this.readableMonth();
};

Date.prototype.readableMonth = function() {
    switch(this.getMonth()) {
        case 0:
            return "Jan";
        case 1:
            return "Feb";
        case 2:
            return "Mar";
        case 3:
            return "Apr";
        case 4:
            return "May";
        case 5:
            return "Jun";
        case 6:
            return "Jul";
        case 7:
            return "Aug";
        case 8:
            return "Sep";
        case 9:
            return "Oct";
        case 10:
            return "Nov";
        case 11:
            return "Dec";
    }
};

Date.prototype.readableYear = function() {
    return "" + this.getFullYear();
};