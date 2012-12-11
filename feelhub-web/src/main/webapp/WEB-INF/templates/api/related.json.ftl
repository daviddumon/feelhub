[
<#list topicDataList as topicData>
{
"id":"${topicData.id}",
"illustrationLink":"${topicData.illustrationLink}",
"name":"${topicData.name}",
"type":"${topicData.type}"
}
${topicData_has_next?string(",", "")}
</#list>
]