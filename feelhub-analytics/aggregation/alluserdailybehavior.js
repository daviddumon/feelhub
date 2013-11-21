load('lib/underscore.js');
load('lib/moment.js');

var workingDate = moment();
var bucket = workingDate.format("YYYY.M.D");
var idStat = workingDate.format("YYYYMD");
var match = {};
match[bucket] = {
	$exists: true
};

var project = {
	stats: "$" + bucket,
	_id: 0
};

var result = _.extend(totals(), usersWithOneFeeling(), usersWithOneTopic());
db.activeuserdailybehavior.update({_id : idStat}, result,{upsert : true});

function totals() {
	return db.userstatistic.aggregate({
		$match: match
	}, {
		$project: project
	}, {
		$group: {
			_id: idStat,
			totalActives: {
				$sum: 1
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
		totalActives: 0,
		totalFeelings : 0,
		totalTopics : 0
	};
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
