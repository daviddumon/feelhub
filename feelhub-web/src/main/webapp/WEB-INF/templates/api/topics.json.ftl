[
<#list topicDatas as topicData>
{
"id":"${topicData.id}",
"sentimentValue":"${topicData.sentimentValue}",
"name":"${topicData.name}",
"illustrationLink":"${topicData.illustrationLink}",
"type":"${topicData.type}"
}${topicData_has_next?string(",", "")}
</#list>
]