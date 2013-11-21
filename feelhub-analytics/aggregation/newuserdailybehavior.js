load('lib/underscore.js');
load('lib/moment.js');

var workingDate = moment();
var bucket = workingDate.format("YYYY.M.D");
var idStat = workingDate.format("YYYYMD");
var match = {};
match[bucket] = {
	$exists: true
};
var start = ISODate(workingDate.clone().startOf('day').format());
var end = ISODate(workingDate.clone().endOf('day').format())
match["creationDate"] = { $gte: start  , $lt: end};
var project = {
	stats: "$" + bucket,
	_id: 0
};

var result = _.extend(totals(), uniqueLogins(), usersWithOneFeeling(), usersWithOneTopic());
db.newuserdailybehavior.update({_id : idStat}, result,{upsert : true});

function totals() {
	return db.userstatistic.aggregate({
		$match: match
	}, {
		$project: project
	}, {
		$group: {
			_id: idStat,
			totalSignups: {
				$sum: 1
			},
			totalLogins: {
				$sum: "$stats.logins"
			},
			totalFeelings: {
				$sum: "$stats.feelings"
			},
			totalTopics: {
				$sum: {
					$add: ["$stats.realTopics", "$stats.httpTopics"]
				}
			}
		}
	}).result[0] || {
		_id : idStat,
		totalSignups: 0,
		totalLogins: 0,
		totalFeelings : 0,
		totalTopics : 0
	};
}

function uniqueLogins() {
	var loggedMatch = _.clone(match);
	loggedMatch[bucket + ".logins"] = {
		$gt: 0
	};
	return db.userstatistic.aggregate({
		$match: loggedMatch
	}, {
		$project: project
	}, {
		$group: {
			_id: idStat,
			uniqueLogins: {
				$sum: 1
			}
		}
	}).result[0];
}

function usersWithOneFeeling() {
	var loggedMatch = _.clone(match);
	loggedMatch[bucket + ".feelings"] = {$gt : 0};
	return db.userstatistic.aggregate({
		$match: loggedMatch
	}, {
		$project: project
	}, {
		$group: {
			_id: idStat,
			postFeeling: {
				$sum: 1
			}
		}
	}).result[0] || {postFeeling : 0};
}

function usersWithOneTopic() {
	return db.userstatistic.aggregate({
		$match: match
	}, {
		$project: {
			_id: 0,
			topics: {
				$add: ["$" + bucket + ".httpTopics", "$" + bucket + ".realTopics"]
			}
		}
	}, {
		$match: {
			topics: {$gt : 0}
		}
	}, {
		$group: {
			_id : idStat,
			postTopic: {$sum : 1}
		}
	}).result[0] || {postTopic : 0};
}
