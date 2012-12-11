[
<#list topicDatas as topicData>
{
"id":"${topicData.id}",
"sentimentValue":"${topicData.sentimentValue}",
"description":"${topicData.description}",
"illustrationLink":"${topicData.illustrationLink}",
"type":"${topicData.type}"
}${topicData_has_next?string(",", "")}
</#list>
]