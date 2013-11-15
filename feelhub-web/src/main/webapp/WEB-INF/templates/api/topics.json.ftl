[
<#list topicDatas as topicData>
{
"id":"${topicData.id}",
"name":"${topicData.name?json_string}",
"thumbnail":"${topicData.thumbnail?json_string}",
"type":"${topicData.type}",
"happyFeelingCount":${topicData.happyFeelingCount?c},
"boredFeelingCount":${topicData.boredFeelingCount?c},
"sadFeelingCount":${topicData.sadFeelingCount?c},
"viewCount":${topicData.viewCount?c},
"popularity":${topicData.popularity?c},
"creationDate":${topicData.creationDate?c},
"lastModificationDate":${topicData.lastModificationDate?c}
}${topicData_has_next?string(",", "")}
</#list>
]