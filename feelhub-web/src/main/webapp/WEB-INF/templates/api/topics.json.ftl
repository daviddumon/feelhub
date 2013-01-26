[
<#list topicDatas as topicData>
{
"id":"${topicData.id}",
"sentimentValue":"${topicData.sentimentValue}",
"name":"${topicData.name?j_string}",
"illustration":"${topicData.illustration?j_string}",
"type":"${topicData.type}"
}${topicData_has_next?string(",", "")}
</#list>
]