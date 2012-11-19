[
<#list topicDataList as topicData>
{
"id":"${topicData.id}",
"illustrationLink":"${topicData.illustrationLink}",
"description":"${topicData.description}",
"type":"${topicData.type}"
}
${topicData_has_next?string(",", "")}
</#list>
]