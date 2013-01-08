[
<#list topicDataList as topicData>
{
"id":"${topicData.id}",
"illustration":"${topicData.illustration}",
"name":"${topicData.name}",
"type":"${topicData.type}"
}
${topicData_has_next?string(",", "")}
</#list>
]