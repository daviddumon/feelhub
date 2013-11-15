[
<#list topicDatas as topicData>
{
"id":"${topicData.id}",
"name":"${topicData.name?json_string}",
"thumbnail":"${topicData.thumbnail?json_string}",
"type":"${topicData.type}",
"happyFeelingCount":"${topicData.happyFeelingCount}",
"boredFeelingCount":"${topicData.boredFeelingCount}",
"sadFeelingCount":"${topicData.sadFeelingCount}",
"creationDate":"${topicData.creationDate}",
"lastModificationDate":"${topicData.lastModificationDate}",
"popularity":"${topicData.popularity}"
}${topicData_has_next?string(",", "")}
</#list>
]